package pt.isel.daw.battleships.service.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.game.GameState
import pt.isel.daw.battleships.repository.games.GamesRepository
import pt.isel.daw.battleships.repository.users.users.UsersRepository
import pt.isel.daw.battleships.service.AuthenticatedService
import pt.isel.daw.battleships.service.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.games.dtos.game.CreateGameRequestDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.service.games.dtos.game.GamesDTO
import pt.isel.daw.battleships.service.games.dtos.game.MatchmakeResponseDTO
import pt.isel.daw.battleships.service.utils.OffsetPageRequest
import pt.isel.daw.battleships.service.utils.findFirstOrNull
import pt.isel.daw.battleships.utils.JwtProvider
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the games.
 *
 * @property gamesRepository the repository of the games
 * @param usersRepository the repository of the users
 * @param jwtProvider the JWT provider
 */
@Service
@Transactional
class GamesServiceImpl(
    private val gamesRepository: GamesRepository,
    usersRepository: UsersRepository,
    jwtProvider: JwtProvider
) : GamesService, AuthenticatedService(usersRepository, jwtProvider) {

    override fun getGames(offset: Int, limit: Int): GamesDTO {
        if (offset < 0 || limit < 0) {
            throw InvalidPaginationParamsException("Offset and limit must be positive")
        }

        if (limit > MAX_GAMES_LIMIT) {
            throw InvalidPaginationParamsException("Limit must be less than $MAX_GAMES_LIMIT")
        }

        return GamesDTO(
            games = gamesRepository
                .findAll(OffsetPageRequest(offset.toLong(), limit))
                .toList()
                .onEach(Game::updateIfPhaseExpired)
                .map(::GameDTO),
            totalCount = gamesRepository.count().toInt()
        )
    }

    override fun createGame(token: String, createGameRequestDTO: CreateGameRequestDTO): Int =
        createGame(
            creator = authenticateUser(token = token),
            createGameRequestDTO = createGameRequestDTO
        ).id ?: throw IllegalStateException("Game id cannot be null")

    override fun matchmake(token: String, gameConfigDTO: GameConfigDTO): MatchmakeResponseDTO {
        val user = authenticateUser(token = token)

        while (true) {
            val game = gamesRepository
                .findAllAvailableGamesWithConfig(
                    config = gameConfigDTO.toGameConfig()
                )
                .filter { game -> game.players.none { it.user == user } }
                .findFirstOrNull()

            if (game == null) {
                val newGame = createGame(
                    creator = user,
                    createGameRequestDTO = CreateGameRequestDTO(
                        name = "Game",
                        config = gameConfigDTO
                    )
                )

                return MatchmakeResponseDTO(
                    game = GameDTO(game = newGame),
                    wasCreated = true
                )
            }

            game.updateIfPhaseExpired()

            if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS) continue

            if (game.hasPlayer(username = user.username)) {
                throw AlreadyJoinedException("You have already joined this game")
            }

            game.addPlayer(player = Player(game = game, user = user))

            game.updatePhase()

            return MatchmakeResponseDTO(
                game = GameDTO(game = game),
                wasCreated = false
            )
        }
    }

    override fun getGame(gameId: Int): GameDTO =
        GameDTO(game = getGameById(gameId = gameId))

    override fun getGameState(gameId: Int): GameStateDTO =
        GameStateDTO(gameState = getGameById(gameId = gameId).state)

    override fun joinGame(token: String, gameId: Int): GameDTO {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)

        joinGame(user = user, game = game)

        return GameDTO(game = game)
    }

    /**
     * Creates a new game.
     *
     * @param creator the user that is creating the game
     * @param createGameRequestDTO the DTO with the game's information
     *
     * @return the new game
     */
    private fun createGame(creator: User, createGameRequestDTO: CreateGameRequestDTO): Game {
        val game = Game(
            name = createGameRequestDTO.name,
            creator = creator,
            config = createGameRequestDTO.config.toGameConfig(),
            state = GameState(phaseExpirationTime = Timestamp.from(Instant.now().plus(1L, ChronoUnit.DAYS)))
        )

        game.addPlayer(player = Player(game = game, user = creator))
        return gamesRepository.save(game)
    }

    /**
     * Joins a game.
     *
     * @param user the user that is joining the game
     * @param game the game to join
     *
     * @throws InvalidPhaseException if the game is not in the matchmaking phase
     * @throws AlreadyJoinedException if the user is already in the game
     */
    private fun joinGame(user: User, game: Game) {
        if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS) {
            throw InvalidPhaseException("Waiting for players phase is over")
        }

        if (game.state.phaseExpired()) {
            game.abortGame()
            throw InvalidPhaseException("Waiting for players phase is over")
        }

        if (game.hasPlayer(username = user.username)) {
            throw AlreadyJoinedException("You have already joined this game")
        }

        game.addPlayer(player = Player(game = game, user = user))
        game.updatePhase()
    }

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game
     *
     * @return the game
     * @throws NotFoundException if the game does not exist
     */
    private fun getGameById(gameId: Int): Game {
        val game = gamesRepository
            .findById(id = gameId)
            ?: throw NotFoundException("Game with id $gameId not found")

        game.updateIfPhaseExpired()

        return game
    }

    companion object {
        const val MAX_GAMES_LIMIT = 100
    }
}
