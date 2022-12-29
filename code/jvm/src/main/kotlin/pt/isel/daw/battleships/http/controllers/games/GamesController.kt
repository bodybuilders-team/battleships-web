package pt.isel.daw.battleships.http.controllers.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModel
import pt.isel.daw.battleships.http.controllers.games.models.games.createGame.CreateGameInputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.createGame.CreateGameOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.getGame.GetGameOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.getGameState.GetGameStateOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.getGames.GetGamesOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.joinGame.JoinGameOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.games.matchmake.MatchmakeOutputModel
import pt.isel.daw.battleships.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SirenEntity
import pt.isel.daw.battleships.http.media.siren.SirenEntity.Companion.SIREN_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.http.media.siren.SubEntity.EmbeddedSubEntity
import pt.isel.daw.battleships.http.pipeline.authentication.Authenticated
import pt.isel.daw.battleships.http.utils.Actions
import pt.isel.daw.battleships.http.utils.Links
import pt.isel.daw.battleships.http.utils.Params
import pt.isel.daw.battleships.http.utils.Rels
import pt.isel.daw.battleships.http.utils.Uris
import pt.isel.daw.battleships.service.games.GamesService
import pt.isel.daw.battleships.utils.JwtProvider.Companion.TOKEN_ATTRIBUTE
import javax.validation.Valid

/**
 * Controller that handles the requests to the /games endpoint.
 *
 * @property gamesService the service that handles the business logic for the games
 */
@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE, PROBLEM_MEDIA_TYPE])
class GamesController(private val gamesService: GamesService) {

    /**
     * Handles the request to get a list of all the games.
     *
     * @param offset the offset of the list of games
     * @param limit the limit of the list of games
     * @param username the username of the player
     * @param excludeUsername the username of the player to exclude
     * @param phases the phases of the games
     * @param ids the ids of the games
     *
     * @return the response to the request with the list of games
     */
    @GetMapping(Uris.GAMES)
    fun getGames(
        @RequestParam(Params.OFFSET_PARAM) offset: Int? = null,
        @RequestParam(Params.LIMIT_PARAM) limit: Int? = null,
        @RequestParam(Params.USERNAME_PARAM) username: String? = null,
        @RequestParam(Params.EXCLUDE_USERNAME_PARAM) excludeUsername: String? = null,
        @RequestParam(Params.GAME_PHASES_PARAM) phases: List<String>? = null,
        @RequestParam(Params.IDS_PARAM) ids: List<Int>? = null
    ): SirenEntity<GetGamesOutputModel> {
        val games = gamesService.getGames(
            offset = offset ?: Params.OFFSET_DEFAULT,
            limit = limit ?: Params.LIMIT_DEFAULT,
            username = username,
            excludeUsername = excludeUsername,
            phases = phases,
            ids = ids
        )

        return SirenEntity(
            `class` = listOf(Rels.LIST_GAMES),
            properties = GetGamesOutputModel(totalCount = games.totalCount),
            links = listOf(
                Links.self(Uris.games())
            ),
            entities = games.games.map { game ->
                EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM, Rels.GAME, "${Rels.GAME}-${game.id}"),
                    properties = GetGameOutputModel(gameDTO = game),
                    links = listOf(
                        Links.self(Uris.gameById(gameId = game.id))
                    ),
                    actions = listOf(
                        Actions.joinGame(gameId = game.id),
                        Actions.leaveGame(gameId = game.id)
                    ),
                    entities = listOf(
                        EmbeddedLink(
                            rel = listOf(Rels.GAME_STATE),
                            href = Uris.gameState(gameId = game.id)
                        )
                    )
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
    ): SirenEntity<CreateGameOutputModel> {
        val gameId = gamesService.createGame(
            token = token,
            createGameRequestDTO = gameData.toCreateGameRequestDTO()
        )

        return SirenEntity(
            `class` = listOf(Rels.CREATE_GAME),
            properties = CreateGameOutputModel(gameId = gameId),
            entities = listOf(
                Links.game(gameId = gameId),
                EmbeddedLink(
                    rel = listOf(Rels.GAME_STATE),
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
            `class` = listOf(Rels.MATCHMAKE),
            properties = MatchmakeOutputModel(matchmakeResponseDTO = matchmakeDTO),
            entities = listOf(
                Links.game(gameId = gameId),
                EmbeddedLink(
                    rel = listOf(Rels.GAME_STATE),
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
            `class` = listOf(Rels.GAME),
            properties = GetGameOutputModel(gameDTO = game),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf(Rels.GAME_STATE),
                    href = Uris.gameState(gameId = gameId)
                )
            ),
            links = listOf(
                Links.self(Uris.gameById(gameId = gameId))
            ),
            actions = listOf(
                Actions.deployFleet(gameId = gameId),
                Actions.getMyFleet(gameId = gameId),
                Actions.getOpponentFleet(gameId = gameId),
                Actions.getMyShots(gameId = gameId),
                Actions.fireShots(gameId = gameId),
                Actions.getOpponentShots(gameId = gameId),
                Actions.getMyBoard(gameId = gameId),
                Actions.leaveGame(gameId = gameId)
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
            `class` = listOf(Rels.GAME_STATE),
            properties = GetGameStateOutputModel(gameStateDTO = gameState),
            entities = listOf(
                Links.game(gameId = gameId)
            ),
            links = listOf(
                Links.self(Uris.gameState(gameId = gameId))
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
        val game = gamesService.joinGame(token = token, gameId = gameId)

        return SirenEntity(
            `class` = listOf(Rels.JOIN_GAME),
            properties = JoinGameOutputModel(gameId = game.id),
            entities = listOf(
                Links.game(gameId = gameId),
                EmbeddedLink(
                    rel = listOf(Rels.GAME_STATE),
                    href = Uris.gameState(gameId = gameId)
                )
            )
        )
    }

    @PostMapping(Uris.GAMES_LEAVE)
    @Authenticated
    fun leaveGame(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int
    ): SirenEntity<Unit> {
        gamesService.leaveGame(token = token, gameId = gameId)

        return SirenEntity(
            `class` = listOf(Rels.LEAVE_GAME),
            entities = listOf(
                Links.game(gameId = gameId),
                EmbeddedLink(
                    rel = listOf(Rels.GAME_STATE),
                    href = Uris.gameState(gameId = gameId)
                )
            )
        )
    }
}
