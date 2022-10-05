package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.database.repositories.UsersRepository
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
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the games.
 *
 * @property gamesRepository the repository of the games
 * @property usersRepository the repository of the users
 * @property jwtProvider the JWT provider
 */
@Service
@Transactional
class GamesService(
    private val gamesRepository: GamesRepository,
    private val usersRepository: UsersRepository,
    private val jwtProvider: JwtProvider
) {
    /**
     * Creates a new game.
     *
     * @param token the token of the user that is creating the game
     * @param createGameRequestDTO the DTO with the game's information
     *
     * @return the id of the new game
     */
    fun createGame(token: String, createGameRequestDTO: CreateGameRequestDTO): Int {
        val user = authenticateUser(token)
        return createGame(user, createGameRequestDTO).id!!
    }

    /**
     * Gets all games.
     *
     * @return the DTO with the information of all games
     */
    fun getGames(): GamesDTO =
        GamesDTO(games = gamesRepository.findAll().map { GameDTO(it) })

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game
     * @return the DTO with the information of the game
     */
    fun getGame(gameId: Int): GameDTO =
        GameDTO(getGameById(gameId))

    /**
     * Gets the state of a game.
     *
     * @param gameId the id of the game
     * @return the DTO with the state of the game
     */
    fun getGameState(gameId: Int): GameStateDTO =
        GameStateDTO(getGameById(gameId).state)

    /**
     * Joins a game.
     *
     * @param token the token of the user that is joining the game
     * @param gameId the id of the game
     *
     * @return the DTO with the information of the game
     * @throws IllegalStateException if the game is already full or the user is already in the game
     */
    fun joinGame(token: String, gameId: Int): GameDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)

        joinGame(user, game)

        if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS)
            throw IllegalStateException("Waiting for players phase is over")

        if (game.getPlayerOrNull(user.username) != null)
            throw IllegalStateException("You have already joined this game")

        game.addPlayer(Player(game, user))
        return GameDTO(game)
    }

    /**
     * Matchmakes a game with a specific configuration.
     *
     * @param token the token of the user that is matchmaking
     * @param gameConfigDTO the DTO with the game's configuration
     *
     * @return the DTO with the information of the matched game
     */
    // TODO: implement nullable game config
    fun matchmake(token: String, gameConfigDTO: GameConfigDTO): MatchmakeDTO {
        val user = authenticateUser(token)
        val game = gamesRepository.findFirstAvailableGameWithConfig(gameConfigDTO.toGameConfig())

        return MatchmakeDTO(
            game = GameDTO(
                if (game == null || game.getPlayerOrNull(user.username) != null)
                    createGame(user, CreateGameRequestDTO("Game", gameConfigDTO))
                else {
                    joinGame(user, game)
                    game
                }
            ),
            wasCreated = game == null || game.getPlayerOrNull(user.username) != null
        )
    }

    /**
     * Authenticates a user.
     *
     * @param token the token of the user
     *
     * @return the authenticated user
     * @throws IllegalStateException if the token is invalid
     * @throws NotFoundException if the user is not found
     */
    fun authenticateUser(token: String): User {
        val tokenPayload = jwtProvider.validateToken(token)
            ?: throw IllegalStateException("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username)
            ?: throw NotFoundException("User not found")
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
        gamesRepository.findById(gameId)
            ?: throw NotFoundException("Game with id $gameId not found")

    /**
     * Creates a new game.
     *
     * @param user the user that is creating the game
     * @param createGameRequestDTO the DTO with the game's information
     *
     * @return the new game
     */
    private fun createGame(user: User, createGameRequestDTO: CreateGameRequestDTO): Game {
        val game = Game(
            name = createGameRequestDTO.name,
            creator = user,
            config = createGameRequestDTO.config.toGameConfig(),
            state = GameState()
        )

        game.addPlayer(Player(game, user))
        return gamesRepository.save(game)
    }

    /**
     * Joins a game.
     *
     * @param user the user that is joining the game
     * @param game the game to join
     *
     * @throws AlreadyJoinedException if the user is already in the game
     * @throws InvalidPhaseException if the game is not in the matchmaking phase
     */
    private fun joinGame(user: User, game: Game) {
        if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS)
            throw InvalidPhaseException("Waiting for players phase is over")

        if (game.getPlayerOrNull(user.username) != null)
            throw AlreadyJoinedException("You have already joined this game")

        game.addPlayer(Player(game, user))
        game.state.phase = GameState.GamePhase.PLACING_SHIPS
    }
}
