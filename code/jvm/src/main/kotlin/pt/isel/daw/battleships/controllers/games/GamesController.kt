package pt.isel.daw.battleships.controllers.games

import org.springframework.web.bind.annotation.*
import pt.isel.daw.battleships.controllers.games.models.game.*
import pt.isel.daw.battleships.controllers.games.models.game.createGame.CreateGameInputModel
import pt.isel.daw.battleships.controllers.games.models.game.createGame.CreateGameOutputModel
import pt.isel.daw.battleships.services.games.GamesService

/**
 * The controller that handles the requests to the /games endpoint.
 *
 * @property gamesService The service that handles the business logic for the games.
 */
@RestController
@RequestMapping("/games")
class GamesController(private val gamesService: GamesService) {

    /**
     * Handles the request to get a list of all the games.
     *
     * @return the response to the request with the list of games.
     */
    @GetMapping
    fun getGames(): GamesOutputModel =
        GamesOutputModel(gamesService.getGames())

    /**
     * Handles the request to create a new game.
     *
     * @param gameData the data of the game to be created.
     *
     * @return the response to the request with the created game.
     */
    @PostMapping
    fun createGame(
        @RequestBody gameData: CreateGameInputModel,
        @RequestAttribute("token") token: String
    ): CreateGameOutputModel =
        CreateGameOutputModel(
            gamesService.createGame(token, gameData.toCreateGameRequest())
        )

    @PostMapping("/matchmake")
    fun matchmake(
        @RequestBody gameConfig: GameConfigModel,
        @RequestAttribute("token") token: String
    ) = MatchmakeModel(gamesService.matchmake(token, gameConfig.toGameConfigDTO()))

    /**
     * Handles the request to get a game.
     *
     * @param gameId the id of the game to be retrieved.
     * @return the game with the given id.
     */
    @GetMapping("/{gameId}")
    fun getGame(
        @PathVariable gameId: Int
    ): GameModel =
        GameModel(gamesService.getGame(gameId))

    /**
     * Handles the request to get the state of a game.
     *
     * @param gameId the id of the game to be retrieved.
     * @return the response to the request with the status of the game with the given id.
     */
    @GetMapping("/{gameId}/state")
    fun getGameState(
        @PathVariable gameId: Int
    ): GameStateModel =
        GameStateModel(gamesService.getGameState(gameId))

    /**
     * Handles the request to join a game.
     *
     * @param gameId the id of the game to be joined.
     * @return the response to the request with the joined game.
     */
    @PostMapping("/{gameId}/join")
    fun joinGame(
        @PathVariable gameId: Int,
        @RequestAttribute("token") token: String
    ): GameModel =
        GameModel(gamesService.joinGame(token, gameId))
}
