package pt.isel.daw.battleships.service.games

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.domain.games.Player
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameState
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.repository.games.GamesRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
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
import pt.isel.daw.battleships.service.utils.findFirstOrNull
import pt.isel.daw.battleships.utils.JwtProvider
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Service that handles the business logic of the games.
 *
 * @property gamesRepository the repository of the games
 * @param usersRepository the repository of the users
 * @param jwtProvider the JWT provider
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class GamesServiceImpl(
    private val gamesRepository: GamesRepository,
    usersRepository: UsersRepository,
    jwtProvider: JwtProvider
) : GamesService, AuthenticatedService(usersRepository, jwtProvider) {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun getGames(
        offset: Int,
        limit: Int,
        username: String?,
        excludeUsername: String?,
        phases: List<String>?,
        ids: List<Int>?
    ): GamesDTO {
        if (offset < 0 || limit < 0)
            throw InvalidPaginationParamsException("Offset and limit must be positive")

        if (limit > MAX_GAMES_LIMIT)
            throw InvalidPaginationParamsException("Limit must be less than $MAX_GAMES_LIMIT")

        val (filteredGames, count) = gamesRepository
            .findAllWithCount(
                username,
                excludeUsername,
                phases,
                ids,
                limit.toLong(),
                offset.toLong()
            )

        return GamesDTO(
            games = filteredGames
                .onEach(Game::updateIfPhaseExpired)
                .let { games ->// Need to filter again because the updateIfPhaseExpired can change the phase
                    if (phases != null)
                        games.filter {
                            it.state.phase in phases.map { phase ->
                                GameState.GamePhase.valueOf(phase)
                            }
                        }
                    else games
                }
                .map(::GameDTO),
            totalCount = count.toInt()
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
                .findAllAvailableGamesWithConfig(config = gameConfigDTO.toGameConfig())
                .filter { game -> game.players.none { it.user == user } }
                .findFirstOrNull()

            if (game == null) {
                val newGame = createGame(
                    creator = user,
                    createGameRequestDTO = CreateGameRequestDTO(
                        name = DEFAULT_GAME_NAME,
                        config = gameConfigDTO
                    )
                )

                return MatchmakeResponseDTO(
                    game = GameDTO(game = newGame),
                    wasCreated = true
                )
            }

            entityManager.lock(game, javax.persistence.LockModeType.PESSIMISTIC_WRITE)

            game.updateIfPhaseExpired()
            if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS) {
                entityManager.lock(game, javax.persistence.LockModeType.NONE)
                continue
            }

            joinGame(user = user, game = game)

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
    private fun createGame(creator: User, createGameRequestDTO: CreateGameRequestDTO): Game =
        gamesRepository.save(
            Game(
                name = createGameRequestDTO.name,
                creator = creator,
                config = createGameRequestDTO.config.toGameConfig(),
                state = GameState()
            )
        )

    override fun leaveGame(token: String, gameId: Int) {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)
        val player = game.getPlayer(username = user.username)

        when (game.state.phase) {
            GameState.GamePhase.FINISHED -> throw InvalidPhaseException("Game has already finished")
            GameState.GamePhase.WAITING_FOR_PLAYERS, GameState.GamePhase.DEPLOYING_FLEETS -> game.abortGame(
                GameState.EndCause.RESIGNATION
            )

            else -> game.finishGame(winner = game.getOpponent(player), cause = GameState.EndCause.RESIGNATION)
        }
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
        entityManager.lock(game, javax.persistence.LockModeType.PESSIMISTIC_WRITE)

        game.updateIfPhaseExpired()

        return game
    }

    companion object {
        private const val MAX_GAMES_LIMIT = 100
        private const val DEFAULT_GAME_NAME = "unnamed Game"
    }
}
