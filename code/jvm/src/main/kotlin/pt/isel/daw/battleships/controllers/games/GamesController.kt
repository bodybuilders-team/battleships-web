package pt.isel.daw.battleships.controllers.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.controllers.games.models.JoinGameModel
import pt.isel.daw.battleships.controllers.games.models.game.GameConfigModel
import pt.isel.daw.battleships.controllers.games.models.game.GameModel
import pt.isel.daw.battleships.controllers.games.models.game.GameStateModel
import pt.isel.daw.battleships.controllers.games.models.game.GamesOutputModel
import pt.isel.daw.battleships.controllers.games.models.game.MatchmakeModel
import pt.isel.daw.battleships.controllers.games.models.game.createGame.CreateGameInputModel
import pt.isel.daw.battleships.controllers.utils.siren.Action
import pt.isel.daw.battleships.controllers.utils.siren.EmbeddedLink
import pt.isel.daw.battleships.controllers.utils.siren.Link
import pt.isel.daw.battleships.controllers.utils.siren.SIREN_TYPE
import pt.isel.daw.battleships.controllers.utils.siren.SirenEntity
import pt.isel.daw.battleships.services.games.GamesService
import pt.isel.daw.battleships.utils.JwtProvider.Companion.TOKEN_ATTRIBUTE
import java.net.URI

/**
 * Controller that handles the requests to the /games endpoint.
 *
 * @property gamesService the service that handles the business logic for the games
 */
@RestController
@RequestMapping("/games", produces = [SIREN_TYPE])
class GamesController(private val gamesService: GamesService) {

    /**
     * Handles the request to get a list of all the games.
     *
     * @return the response to the request with the list of games
     */
    @GetMapping
    fun getGames(): SirenEntity<GamesOutputModel> {
        val games = gamesService.getGames()

        return SirenEntity(
            `class` = listOf("games"),
            properties = GamesOutputModel(games),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games")
                )
            ),
            entities = games.games.map {
                EmbeddedLink(
                    rel = listOf("item", "game-${it.id}"),
                    href = URI("/games/${it.id}")
                )
            },
            actions = listOf(
                Action(
                    name = "join-game",
                    title = "Join Game",
                    method = "POST",
                    href = URI("/games/{gameId}/join")
                )
            )
        )
    }

    /**
     * Handles the request to create a new game.
     *
     * @param gameData the data of the game to be created
     * @param token the token of the user that is creating the game
     *
     * @return the response to the request with the created game
     */
    @PostMapping
    fun createGame(
        @RequestBody gameData: CreateGameInputModel,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<Unit> {
        val gameId = gamesService.createGame(token, gameData.toCreateGameRequestDTO())

        return SirenEntity(
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                ),
                EmbeddedLink(
                    rel = listOf("state"),
                    href = URI("/games/$gameId/state")
                )
            )
        )
    }

    /**
     * Handles the request to matchmake a game with a specific configuration.
     *
     * @param gameConfig the configuration of the game to be matched
     * @param token the token of the user that is matchmaking
     *
     * @return the response to the request with the matched game
     */
    @PostMapping("/matchmake")
    fun matchmake(
        @RequestBody gameConfig: GameConfigModel,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<MatchmakeModel> {
        val matchmakeDTO = gamesService.matchmake(token, gameConfig.toGameConfigDTO())
        val gameId = matchmakeDTO.game.id

        return SirenEntity(
            `class` = listOf("matchmake"),
            properties = MatchmakeModel(matchmakeDTO),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                ),
                EmbeddedLink(
                    rel = listOf("state"),
                    href = URI("/games/$gameId/state")
                )
            )
        )
    }

    /**
     * Handles the request to get a game.
     *
     * @param gameId the id of the game to be retrieved
     * @return the game with the given id
     */
    @GetMapping("/{gameId}")
    fun getGame(
        @PathVariable gameId: Int
    ): SirenEntity<GameModel> {
        val game = gamesService.getGame(gameId)

        return SirenEntity(
            `class` = listOf("game"),
            properties = GameModel(game),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("state"),
                    href = URI("/games/$gameId/state")
                )
            ),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId")
                )
            ),
            actions = listOf(
                Action(
                    name = "join",
                    title = "Join Game",
                    method = "POST",
                    href = URI("/games/$gameId/join")
                )
            )
        )
    }

    /**
     * Handles the request to get the state of a game.
     *
     * @param gameId the id of the game to be retrieved
     * @return the response to the request with the status of the game with the given id
     */
    @GetMapping("/{gameId}/state")
    fun getGameState(
        @PathVariable gameId: Int
    ): SirenEntity<GameStateModel> {
        val gameState = gamesService.getGameState(gameId)

        return SirenEntity(
            `class` = listOf("game-state"),
            properties = GameStateModel(gameId, gameState),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                )
            ),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId/state")
                )
            ),
            actions = listOf(
                Action(
                    name = "deployFleet",
                    title = "Deploy Fleet",
                    method = "POST",
                    href = URI("/games/$gameId/players/self/fleet")
                ),
                Action(
                    name = "getMyFleet",
                    title = "Get My Fleet",
                    method = "GET",
                    href = URI("/games/$gameId/players/self/fleet")
                ),
                Action(
                    name = "getOpponentFleet",
                    title = "Get Opponent Fleet",
                    method = "GET",
                    href = URI("/games/$gameId/players/opponent/fleet")
                ),
                Action(
                    name = "getMyShots",
                    title = "Get My Shots",
                    method = "GET",
                    href = URI("/games/$gameId/players/self/shots")
                ),
                Action(
                    name = "getOpponentShots",
                    title = "Get Opponent Shots",
                    method = "GET",
                    href = URI("/games/$gameId/players/opponent/shots")
                ),
                Action(
                    name = "shoot",
                    title = "Shoot",
                    method = "POST",
                    href = URI("/games/$gameId/players/self/shots")
                )
            )
        )
    }

    /**
     * Handles the request to join a game.
     *
     * @param gameId the id of the game to be joined
     * @param token the token of the user that is joining the game
     *
     * @return the response to the request with the joined game
     */
    @PostMapping("/{gameId}/join")
    fun joinGame(
        @PathVariable gameId: Int,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<JoinGameModel> {
        val game = gamesService.joinGame(token, gameId)

        return SirenEntity(
            `class` = listOf("join-game"),
            properties = JoinGameModel(game.id),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                ),
                EmbeddedLink(
                    rel = listOf("state"),
                    href = URI("/games/$gameId/state")
                )
            )
        )
    }
}
