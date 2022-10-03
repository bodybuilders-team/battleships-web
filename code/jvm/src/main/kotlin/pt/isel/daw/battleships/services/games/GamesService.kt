package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.model.player.Player
import pt.isel.daw.battleships.database.model.player.PlayerId
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.games.dtos.CreateGameDTO
import pt.isel.daw.battleships.services.games.dtos.GameDTO
import pt.isel.daw.battleships.services.games.dtos.GameStateDTO
import pt.isel.daw.battleships.services.games.dtos.GamesDTO
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
        val tokenPayload = jwtUtils.validateToken(token) ?: throw IllegalStateException("Invalid token")

        val user = usersRepository.findByUsername(tokenPayload.username) ?: throw NotFoundException("User not found")

        val game = Game(
            name = createGameDTO.name,
            creator = user,
            config = createGameDTO.config.toGameConfig(),
            state = GameState()
        )

        game.addPlayer(Player(PlayerId(game, user)))

        gamesRepository.save(game)

        return game.id!!
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
        val tokenPayload = jwtUtils.validateToken(token) ?: throw IllegalStateException("Invalid token")

        val user = usersRepository.findByUsername(tokenPayload.username) ?: throw NotFoundException("User not found")

        val game = getGameById(gameId)

        if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS) {
            throw IllegalStateException("Waiting for players phase is over")
        }

        if (game.getPlayer(user.username) != null) {
            throw IllegalStateException("You have already joined this game")
        }

        game.addPlayer(Player(PlayerId(game, user)))

        return GameDTO(game)
    }

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game.
     *
     * @return the game
     * @throws NotFoundException if the game does not exist.
     */
    private fun getGameById(gameId: Int): Game = gamesRepository
        .findById(gameId)
        .orElseThrow { NotFoundException("GameResponse with id $gameId not found") }
}
