package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.services.exceptions.BadRequestException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import javax.transaction.Transactional

/**
 * GameResponse services.
 *
 * @property gamesRepository The games' repository.
 */
@Service
@Transactional
class GamesService(
    private val gamesRepository: GamesRepository
) {
    /**
     * Creates a new game.
     *
     * @param createGameRequest the request for the creation of a new game.
     * @return the id of the new game.
     */
    fun createGame(createGameRequest: CreateGameRequest): Int {
        val game = Game(
            name = createGameRequest.name,
            player1 = createGameRequest.player1,
            config = createGameRequest.config,
            state = GameState()
        )
        gamesRepository.save(game)

        return game.id
    }

    /**
     * Gets all games.
     *
     * @return the response with all games.
     */
    fun getGames(): GamesResponse =
        GamesResponse(games = gamesRepository.findAll().toList())

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game.
     * @return the response with the game.
     */
    fun getGame(gameId: Int): Game =
        getGameById(gameId)

    /**
     * Gets the state of a game.
     *
     * @param gameId the id of the game.
     * @return the response with the game state.
     */
    fun getGameState(gameId: Int): GameState =
        getGameById(gameId).state

    /**
     * Joins a game.
     *
     * @param gameId the id of the game.
     * @return the response with the game.
     */
    fun joinGame(gameId: Int): Game {
        val game = getGameById(gameId)

        if (game.state.phase != GameState.GamePhase.WAITING_FOR_PLAYERS) {
            throw BadRequestException("GameResponse is not waiting for players")
        }

        return game
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
