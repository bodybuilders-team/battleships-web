package pt.isel.daw.battleships.http.controllers.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.Params
import pt.isel.daw.battleships.http.Uris
import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModel
import pt.isel.daw.battleships.http.controllers.games.models.games.createGame.CreateGameInputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.getGame.GetGameOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.getGameState.GetGameStateOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.getGames.GetGamesOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.joinGame.JoinGameOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.matchmake.MatchmakeOutputModel
import pt.isel.daw.battleships.http.pipeline.authentication.Authenticated
import pt.isel.daw.battleships.http.siren.Action
import pt.isel.daw.battleships.http.siren.Link
import pt.isel.daw.battleships.http.siren.SirenEntity
import pt.isel.daw.battleships.http.siren.SirenEntity.Companion.SIREN_TYPE
import pt.isel.daw.battleships.http.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.service.games.GamesService
import pt.isel.daw.battleships.utils.JwtProvider.Companion.TOKEN_ATTRIBUTE
import javax.validation.Valid

/**
 * Controller that handles the requests to the /games endpoint.
 *
 * @property gamesService the service that handles the business logic for the games
 */
@RestController
@RequestMapping(produces = [SIREN_TYPE])
class GamesController(private val gamesService: GamesService) {

    /**
     * Handles the request to get a list of all the games.
     *
     * @param offset the offset of the list of games
     * @param limit the limit of the list of games
     *
     * @return the response to the request with the list of games
     */
    @GetMapping(Uris.GAMES)
    fun getGames(
        @RequestParam(Params.OFFSET_PARAM) offset: Int,
        @RequestParam(Params.LIMIT_PARAM) limit: Int
    ): SirenEntity<GetGamesOutputModel> {
        val games = gamesService.getGames(offset = offset, limit = limit)

        return SirenEntity(
            `class` = listOf("games"),
            properties = GetGamesOutputModel(gamesDTO = games),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.games()
                )
            ),
            entities = games.games.map {
                EmbeddedLink(
                    rel = listOf("item", "game-${it.id}"),
                    href = Uris.gameById(it.id),
                    title = "Game ${it.id}"
                )
            }
        )
    }

    /**
     * Handles the request to create a new game.
     *
     * @param token the token of the user that is creating the game
     * @param gameData the data of the game to be created
     *
     * @return the response to the request with the created game
     */
    @PostMapping(Uris.GAMES)
    @Authenticated
    fun createGame(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @Valid @RequestBody
        gameData: CreateGameInputModel
    ): SirenEntity<Unit> {
        val gameId = gamesService.createGame(
            token = token,
            createGameRequestDTO = gameData.toCreateGameRequestDTO()
        )

        return SirenEntity(
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                ),
                EmbeddedLink(
                    rel = listOf("state"),
                    href = Uris.gameState(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to matchmake a game with a specific configuration.
     *
     * @param token the token of the user that is matchmaking
     * @param gameConfig the configuration of the game to be matched
     *
     * @return the response to the request with the matched game
     */
    @PostMapping(Uris.GAMES_MATCHMAKE)
    @Authenticated
    fun matchmake(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @Valid @RequestBody
        gameConfig: GameConfigModel
    ): SirenEntity<MatchmakeOutputModel> {
        val matchmakeDTO = gamesService.matchmake(
            token = token,
            gameConfigDTO = gameConfig.toGameConfigDTO()
        )
        val gameId = matchmakeDTO.game.id

        return SirenEntity(
            `class` = listOf("matchmake"),
            properties = MatchmakeOutputModel(matchmakeResponseDTO = matchmakeDTO),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                ),
                EmbeddedLink(
                    rel = listOf("state"),
                    href = Uris.gameState(gameId = gameId)
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
    @GetMapping(Uris.GAMES_GET_BY_ID)
    fun getGame(
        @PathVariable gameId: Int
    ): SirenEntity<GetGameOutputModel> {
        val game = gamesService.getGame(gameId = gameId)

        return SirenEntity(
            `class` = listOf("game"),
            properties = GetGameOutputModel(gameDTO = game),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("state"),
                    href = Uris.gameState(gameId = gameId)
                )
            ),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.gameById(gameId = game.id)
                )
            ),
            actions = listOf(
                Action(
                    name = "join",
                    title = "Join Game",
                    method = "POST",
                    href = Uris.gameJoin(gameId = gameId)
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
    @GetMapping(Uris.GAMES_GAME_STATE)
    fun getGameState(
        @PathVariable gameId: Int
    ): SirenEntity<GetGameStateOutputModel> {
        val gameState = gamesService.getGameState(gameId = gameId)

        return SirenEntity(
            `class` = listOf("game-state"),
            properties = GetGameStateOutputModel(gameStateDTO = gameState),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            ),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.gameState(gameId = gameId)
                )
            ),
            actions = listOf(
                Action(
                    name = "deployFleet",
                    title = "Deploy Fleet",
                    method = "POST",
                    href = Uris.myFleet(gameId = gameId)
                ),
                Action(
                    name = "getMyFleet",
                    title = "Get My Fleet",
                    method = "GET",
                    href = Uris.myFleet(gameId = gameId)
                ),
                Action(
                    name = "getOpponentFleet",
                    title = "Get Opponent Fleet",
                    method = "GET",
                    href = Uris.opponentFleet(gameId = gameId)
                ),
                Action(
                    name = "getMyShots",
                    title = "Get My Shots",
                    method = "GET",
                    href = Uris.myShots(gameId = gameId)
                ),
                Action(
                    name = "getOpponentShots",
                    title = "Get Opponent Shots",
                    method = "GET",
                    href = Uris.opponentShots(gameId = gameId)
                ),
                Action(
                    name = "fireShots",
                    title = "Fire",
                    method = "POST",
                    href = Uris.myShots(gameId = gameId)
                ),
                Action(
                    name = "getMyBoard",
                    title = "Get My Board",
                    method = "GET",
                    href = Uris.myBoard(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to join a game.
     *
     * @param token the token of the user that is joining the game
     * @param gameId the id of the game to be joined
     *
     * @return the response to the request with the joined game
     */
    @PostMapping(Uris.GAMES_JOIN)
    @Authenticated
    fun joinGame(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int
    ): SirenEntity<JoinGameOutputModel> {
        val game = gamesService.joinGame(
            token = token,
            gameId = gameId
        )

        return SirenEntity(
            `class` = listOf("join-game"),
            properties = JoinGameOutputModel(gameId = game.id),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = game.id)
                ),
                EmbeddedLink(
                    rel = listOf("state"),
                    href = Uris.gameState(gameId = gameId)
                )
            )
        )
    }
}
