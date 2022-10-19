package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.database.repositories.games.GamesRepository
import pt.isel.daw.battleships.dtos.games.game.CreateGameInputDTO
import pt.isel.daw.battleships.dtos.games.game.GameConfigDTO
import pt.isel.daw.battleships.dtos.games.game.GameDTO
import pt.isel.daw.battleships.dtos.games.game.GameStateDTO
import pt.isel.daw.battleships.dtos.games.game.GamesDTO
import pt.isel.daw.battleships.dtos.games.game.MatchmakeDTO
import pt.isel.daw.battleships.services.AuthenticatedService
import pt.isel.daw.battleships.services.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.services.exceptions.InvalidPaginationParams
import pt.isel.daw.battleships.services.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.utils.OffsetPageRequest
import pt.isel.daw.battleships.utils.JwtProvider
import java.sql.Timestamp
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
            throw InvalidPaginationParams("Offset and limit must be positive")
        }

        if (limit > MAX_GAMES_LIMIT) {
            throw InvalidPaginationParams("Limit must be less than $MAX_GAMES_LIMIT")
        }

        return gamesRepository
            .findAll(OffsetPageRequest(offset.toLong(), limit))
            .toList()
            .map { GameDTO(it) }
            .let { games ->
                GamesDTO(
                    games = games,
                    totalCount = usersRepository.count().toInt()
                )
            }
    }

    override fun createGame(token: String, createGameRequestDTO: CreateGameInputDTO): Int =
        createGame(
            creator = authenticateUser(token),
            createGameRequestDTO = createGameRequestDTO
        ).id ?: throw IllegalStateException("Game ID is null")

    override fun matchmake(token: String, gameConfigDTO: GameConfigDTO): MatchmakeDTO {
        val user = authenticateUser(token)

        val (game, wasCreated) = gamesRepository
            .findFirstAvailableGameWithConfig(user, gameConfigDTO.toGameConfig())
            ?.let { foundGame ->
                joinGame(user = user, game = foundGame)
                foundGame to false
            }
            ?: (
                createGame(
                    creator = user,
                    createGameRequestDTO = CreateGameInputDTO(name = "Game", config = gameConfigDTO)
                ) to true
                )

        return MatchmakeDTO(
            game = GameDTO(game),
            wasCreated = wasCreated
        )
    }

    override fun getGame(gameId: Int): GameDTO =
        GameDTO(game = getGameById(gameId))

    override fun getGameState(gameId: Int): GameStateDTO =
        GameStateDTO(phase = getGameById(gameId).state)

    override fun joinGame(token: String, gameId: Int): GameDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)

        joinGame(user, game)

        return GameDTO(game)
    }

    /**
     * Creates a new game.
     *
     * @param creator the user that is creating the game
     * @param createGameRequestDTO the DTO with the game's information
     *
     * @return the new game
     */
    private fun createGame(creator: User, createGameRequestDTO: CreateGameInputDTO): Game {
        val game = Game(
            name = createGameRequestDTO.name,
            creator = creator,
            config = createGameRequestDTO.config.toGameConfig(),
            state = GameState()
        )

        game.addPlayer(
            player = Player(game = game, user = creator)
        )
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

        if (game.hasPlayer(user.username)) {
            throw AlreadyJoinedException("You have already joined this game")
        }

        game.addPlayer(
            player = Player(game = game, user = user)
        )
        game.state.phase = GameState.GamePhase.GRID_LAYOUT
        game.state.phaseEndTime = Timestamp(System.currentTimeMillis() + game.config.maxTimeForLayoutPhase)
    }

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game
     *
     * @return the game
     * @throws NotFoundException if the game does not exist
     */
    private fun getGameById(gameId: Int): Game =
        gamesRepository
            .findById(gameId)
            ?: throw NotFoundException("Game with id $gameId not found")

    companion object {
        const val MAX_GAMES_LIMIT = 100
    }
}
