package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.Game
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import javax.transaction.Transactional

/**
 * Game services.
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
        val game = Game()
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
    fun getGame(gameId: Int): GameResponse =
        GameResponse(game = getGameById(gameId))

    /**
     * Gets the state of a game.
     *
     * @param gameId the id of the game.
     * @return the response with the game state.
     */
    fun getGameState(gameId: Int): GameStateResponse =
        GameStateResponse(state = getGameById(gameId).state)

    /**
     * Joins a game.
     *
     * @param gameId the id of the game.
     * @return the response with the game.
     */
    fun joinGame(gameId: Int): GameResponse {
        val game = getGameById(gameId)

        /*TODO: if (game.state != GameState.WAITING_FOR_PLAYERS) {
            throw BadRequestException("Game is not waiting for players")
        }*/

        return GameResponse(game)
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
        .orElseThrow { NotFoundException("Game with id $gameId not found") }
}
