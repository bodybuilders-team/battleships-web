package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.database.repositories.games.GamesRepository
import pt.isel.daw.battleships.services.AuthenticatedService
import pt.isel.daw.battleships.services.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.services.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.games.dtos.game.CreateGameRequestDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.services.games.dtos.game.GamesDTO
import pt.isel.daw.battleships.services.games.dtos.game.MatchmakeDTO
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

    override fun createGame(token: String, createGameRequestDTO: CreateGameRequestDTO): Int =
        createGame(
            creator = authenticateUser(token),
            createGameRequestDTO = createGameRequestDTO
        ).id!!

    override fun getGames(): GamesDTO {
        val games = gamesRepository.findAll().map { GameDTO(it) }
        return GamesDTO(games, games.size)
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

    override fun matchmake(token: String, gameConfigDTO: GameConfigDTO): MatchmakeDTO {
        val user = authenticateUser(token)

        val (game, wasCreated) = gamesRepository
            .findFirstAvailableGameWithConfig(gameConfigDTO.toGameConfig())
            ?.let { foundGame ->
                joinGame(user, foundGame)
                foundGame to false
            }
            ?: (createGame(creator = user, CreateGameRequestDTO("Game", gameConfigDTO)) to true)

        return MatchmakeDTO(
            game = GameDTO(game),
            wasCreated = wasCreated
        )
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
            state = GameState()
        )

        game.addPlayer(Player(game, creator))
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

        game.addPlayer(Player(game, user))
        game.state.phase = GameState.GamePhase.PLACING_SHIPS
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
}
