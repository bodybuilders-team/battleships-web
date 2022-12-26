package pt.isel.daw.battleships.service.games

import pt.isel.daw.battleships.domain.exceptions.UserNotInGameException
import pt.isel.daw.battleships.domain.exceptions.WaitingForPlayersTimeExpiredException
import pt.isel.daw.battleships.service.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.games.dtos.game.CreateGameRequestDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.service.games.dtos.game.GamesDTO
import pt.isel.daw.battleships.service.games.dtos.game.MatchmakeResponseDTO

/**
 * Service that handles the business logic of the games.
 */
interface GamesService {

    /**
     * Gets all games.
     *
     * @param offset the offset of the pagination
     * @param limit the limit of the pagination
     *
     * @param username the username of the user that must be in the game
     * @param excludeUsername the username of the user that must not be in the game
     * @param phases the phases that the game must be in
     * @param ids the ids of the games
     *
     * @return the DTO with the information of the games
     * @throws InvalidPaginationParamsException if the offset or limit are invalid
     * @throws NotFoundException if the user is not found
     */
    fun getGames(
        offset: Int,
        limit: Int,
        username: String?,
        excludeUsername: String?,
        phases: List<String>?,
        ids: List<Int>?
    ): GamesDTO

    /**
     * Creates a new game.
     *
     * @param token the token of the user that is creating the game
     * @param createGameRequestDTO the DTO with the game's information
     *
     * @return the id of the new game
     * @throws IllegalStateException if the game's id is null
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     */
    fun createGame(token: String, createGameRequestDTO: CreateGameRequestDTO): Int

    /**
     * Matchmakes a game with a specific configuration.
     *
     * @param token the token of the user that is matchmaking
     * @param gameConfigDTO the DTO with the game's configuration
     *
     * @return the DTO with the information of the matched game
     *
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws AlreadyJoinedException if the user is already in a found game
     * @throws WaitingForPlayersTimeExpiredException if the waiting for players phase has expired
     */
    fun matchmake(token: String, gameConfigDTO: GameConfigDTO): MatchmakeResponseDTO

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game
     *
     * @return the DTO with the information of the game
     * @throws NotFoundException if the game does not exist
     */
    fun getGame(gameId: Int): GameDTO

    /**
     * Gets the state of a game.
     *
     * @param gameId the id of the game
     *
     * @return the DTO with the state of the game
     * @throws NotFoundException if the game does not exist
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
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found or the game is not found
     */
    fun joinGame(token: String, gameId: Int): GameDTO

    /**
     * Aborts a game.
     *
     * @param token the token of the user that is aborting the game
     * @param gameId the id of the game
     *
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found or the game is not found
     * @throws UserNotInGameException if the user is not in the game
     */
    fun leaveGame(token: String, gameId: Int)
}
