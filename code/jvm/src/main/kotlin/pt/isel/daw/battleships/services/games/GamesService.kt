package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.dtos.games.game.CreateGameInputDTO
import pt.isel.daw.battleships.dtos.games.game.GameConfigDTO
import pt.isel.daw.battleships.dtos.games.game.GameDTO
import pt.isel.daw.battleships.dtos.games.game.GameStateDTO
import pt.isel.daw.battleships.dtos.games.game.GamesDTO
import pt.isel.daw.battleships.dtos.games.game.MatchmakeDTO
import pt.isel.daw.battleships.services.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.services.exceptions.InvalidPhaseException

/**
 * Service that handles the business logic of the games.
 */
interface GamesService {

    /**
     * Gets all games.
     *
     * @return the DTO with the information of all games
     */
    fun getGames(): GamesDTO

    /**
     * Creates a new game.
     *
     * @param token the token of the user that is creating the game
     * @param createGameRequestDTO the DTO with the game's information
     *
     * @return the id of the new game
     */
    fun createGame(token: String, createGameRequestDTO: CreateGameInputDTO): Int

    /**
     * Matchmakes a game with a specific configuration.
     *
     * @param token the token of the user that is matchmaking
     * @param gameConfigDTO the DTO with the game's configuration
     *
     * @return the DTO with the information of the matched game
     */
    fun matchmake(token: String, gameConfigDTO: GameConfigDTO): MatchmakeDTO

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game
     * @return the DTO with the information of the game
     */
    fun getGame(gameId: Int): GameDTO

    /**
     * Gets the state of a game.
     *
     * @param gameId the id of the game
     * @return the DTO with the state of the game
     */
    fun getGameState(gameId: Int): GameStateDTO

    /**
     * Joins a game.
     *
     * @param token the token of the user that is joining the game
     * @param gameId the id of the game
     *
     * @return the DTO with the information of the game
     * @throws InvalidPhaseException if the game is not in the matchmaking phase
     * @throws AlreadyJoinedException if the user is already in the game
     */
    fun joinGame(token: String, gameId: Int): GameDTO
}
