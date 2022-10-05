package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.model.player.Player
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.services.exceptions.AuthenticationError
import pt.isel.daw.battleships.services.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.games.dtos.CreateGameDTO
import pt.isel.daw.battleships.services.games.dtos.GameConfigDTO
import pt.isel.daw.battleships.services.games.dtos.GameDTO
import pt.isel.daw.battleships.services.games.dtos.GameStateDTO
import pt.isel.daw.battleships.services.games.dtos.GamesDTO
import pt.isel.daw.battleships.services.games.dtos.MatchmakeDTO
import pt.isel.daw.battleships.utils.JwtUtils
import javax.transaction.Transactional

/**
 * GameResponse services.
 *
 * @property gamesRepository The games' repository.
 */
@Service
@Transactional
class GamesService(
    private val gamesRepository: GamesRepository,
    private val usersRepository: UsersRepository,
    private val jwtUtils: JwtUtils
) {
    /**
     * Creates a new game.
     *
     * @param createGameDTO the request for the creation of a new game.
     * @return the id of the new game.
     */
    fun createGame(token: String, createGameDTO: CreateGameDTO): Int {
        val user = authenticateUser(token)
        return createGame(user, createGameDTO).id!!
    }

    /**
     * Gets all games.
     *
     * @return the response with all games.
     */
    fun getGames(): GamesDTO =
        GamesDTO(games = gamesRepository.findAll().map { GameDTO(it) })

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game.
     * @return the response with the game.
     */
    fun getGame(gameId: Int): GameDTO =
        GameDTO(getGameById(gameId))

    /**
     * Gets the state of a game.
     *
     * @param gameId the id of the game.
     * @return the response with the game state.
     */
    fun getGameState(gameId: Int): GameStateDTO =
        GameStateDTO(getGameById(gameId).state)

    /**
     * Joins a game.
     *
     * @param gameId the id of the game.
     * @return the response with the game.
     */
    fun joinGame(token: String, gameId: Int): GameDTO {
        val user = authenticateUser(token)
        val game = gamesRepository.findById(gameId) ?: throw NotFoundException("Game not found")
        joinGame(user, game)

        return GameDTO(game)
    }

    // TODO: implement nullable game config
    fun matchmake(token: String, config: GameConfigDTO): MatchmakeDTO {
        val user = authenticateUser(token)

        val game = gamesRepository.findFirstAvailableGameWithConfig(
            config.toGameConfig()
        )

        return if (game == null || game.getPlayer(user.username) != null) {
            val newGame = createGame(user, CreateGameDTO("Game", config))
            MatchmakeDTO(GameDTO(newGame), true)
        } else {
            joinGame(user, game)
            MatchmakeDTO(GameDTO(game), false)
        }
    }

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game.
     *
     * @return the game
     * @throws NotFoundException if the game does not exist.
     */
    private fun getGameById(gameId: Int): Game =
        gamesRepository.findById(gameId) ?: throw NotFoundException("GameResponse with id $gameId not found")

    private fun authenticateUser(token: String): User {
        val tokenPayload = jwtUtils.validateToken(token) ?: throw AuthenticationError("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username) ?: throw NotFoundException("User not found")
    }

    private fun createGame(user: User, createGameDTO: CreateGameDTO): Game {
        val game = Game(
            name = createGameDTO.name,
            creator = user,
            config = createGameDTO.config.toGameConfig(),
            state = GameState()
        )

        game.addPlayer(Player(game, user))

        return gamesRepository.save(game)
    }

    private fun joinGame(user: User, game: Game) {
        if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS) {
            throw InvalidPhaseException("Waiting for players phase is over")
        }

        if (game.getPlayer(user.username) != null) {
            throw AlreadyJoinedException("You have already joined this game")
        }

        game.addPlayer(Player(game, user))

        game.state.phase = GameState.GamePhase.PLACING_SHIPS
    }
}
