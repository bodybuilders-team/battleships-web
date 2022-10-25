package pt.isel.daw.battleships.http.controllers.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.Uris
import pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet.DeployFleetInputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet.DeployFleetOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.fireShots.FireShotsInputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.fireShots.FireShotsOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.getBoard.GetBoardOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.getFleet.GetFleetOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.getOpponentFleet.GetOpponentFleetOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.getOpponentShots.GetOpponentShotsOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.getShots.GetShotsOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotsModel
import pt.isel.daw.battleships.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.Link
import pt.isel.daw.battleships.http.media.siren.SirenEntity
import pt.isel.daw.battleships.http.media.siren.SirenEntity.Companion.SIREN_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.http.pipeline.authentication.Authenticated
import pt.isel.daw.battleships.service.games.GamesService
import pt.isel.daw.battleships.service.games.PlayersService
import pt.isel.daw.battleships.service.games.dtos.shot.UnfiredShotsDTO
import pt.isel.daw.battleships.utils.JwtProvider.Companion.TOKEN_ATTRIBUTE
import javax.validation.Valid

/**
 * Controller that handles the requests to the game's player's resources.
 *
 * @property playersService the service that handles the requests to the game's player's resources
 * @property gamesService the service that handles the requests to the game's resources
 */
@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE, PROBLEM_MEDIA_TYPE])
@Authenticated
class PlayersController(
    private val playersService: PlayersService,
    private val gamesService: GamesService
) {

    /**
     * Handles the request to get the fleet of a player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the player
     */
    @GetMapping(Uris.PLAYERS_MY_FLEET)
    fun getFleet(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int
    ): SirenEntity<GetFleetOutputModel> {
        val ships = playersService
            .getFleet(token = token, gameId = gameId)
            .ships
            .map(::DeployedShipModel)

        return SirenEntity(
            `class` = listOf("my-fleet"),
            properties = GetFleetOutputModel(ships = ships),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.myFleet(gameId = gameId)
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to deploy a fleet.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param fleet the ships to be deployed
     */
    @PostMapping(Uris.PLAYERS_MY_FLEET)
    fun deployFleet(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int,
        @Valid @RequestBody
        fleet: DeployFleetInputModel
    ): SirenEntity<DeployFleetOutputModel> {
        playersService
            .deployFleet(
                token = token,
                gameId = gameId,
                fleetDTO = fleet.toUndeployedFleetDTO()
            )

        return SirenEntity(
            properties = DeployFleetOutputModel(successfullyDeployed = true),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("fleet"),
                    href = Uris.myFleet(gameId = gameId)
                ),
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to get the board of a player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the board of the player
     */
    @GetMapping(Uris.PLAYERS_MY_BOARD)
    fun getBoard(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int
    ): SirenEntity<GetBoardOutputModel> {
        val config = gamesService.getGame(gameId = gameId).config
        val types = config.shipTypes
        val boardSize = config.gridSize

        val rows = Array(boardSize) { CharArray(boardSize) { '·' } }

        val fleet = playersService.getFleet(token = token, gameId = gameId).ships
        val shots = playersService.getOpponentShots(
            token = token,
            gameId = gameId
        ).shots

        fleet.forEach { ship ->
            val shipTypeIndex = types.indexOfFirst { it.shipName == ship.type }
            val shipSize = types[shipTypeIndex].size
            val shipOrientation = ship.orientation
            val shipPosition = ship.coordinate

            val shipStartX = shipPosition.col - 'A'
            val shipStartY = shipPosition.row - 1

            val shipEndX = if (shipOrientation == "HORIZONTAL") shipStartX + shipSize - 1 else shipStartX
            val shipEndY = if (shipOrientation == "VERTICAL") shipStartY + shipSize - 1 else shipStartY

            for (x in shipStartX..shipEndX) {
                for (y in shipStartY..shipEndY) {
                    rows[y][x] = '0' + shipTypeIndex
                }
            }
        }

        shots.forEach { shot ->
            val shotX = shot.coordinate.col - 'A'
            val shotY = shot.coordinate.row - 1

            rows[shotY][shotX] =
                if (
                    shot.result.result == "HIT" ||
                    shot.result.result == "SUNK"
                ) 'X' else 'M'
        }

        val board = rows.map(::String)

        return SirenEntity(
            `class` = listOf("my-board"),
            properties = GetBoardOutputModel(board = board),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.myFleet(gameId = gameId)
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to get the opponent's fleet.
     * Only gets those that are sunk.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the opponent
     */
    @GetMapping(Uris.PLAYERS_OPPONENT_FLEET)
    fun getOpponentFleet(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int
    ): SirenEntity<GetOpponentFleetOutputModel> {
        val ships = playersService
            .getOpponentFleet(token = token, gameId = gameId)
            .ships
            .map(::DeployedShipModel)

        return SirenEntity(
            `class` = listOf("opponent-fleet"),
            properties = GetFleetOutputModel(ships = ships),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.opponentFleet(gameId = gameId)
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to get the shots of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return te shots of the player
     */
    @GetMapping(Uris.PLAYERS_MY_SHOTS)
    fun getShots(
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String,
        @PathVariable gameId: Int
    ): SirenEntity<GetShotsOutputModel> {
        val shots = playersService
            .getShots(token = token, gameId = gameId)
            .shots
            .map(::FiredShotModel)

        return SirenEntity(
            `class` = listOf("my-shots"),
            properties = FiredShotsModel(shots = shots),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.myShots(gameId = gameId)
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to shoot at the opponent's fleet.
     *
     * @param gameId the id of the game
     * @param shots the shots to be created
     * @param token the token of the user
     *
     * @return the shots made
     */
    @PostMapping(Uris.PLAYERS_MY_SHOTS)
    fun fireShots(
        @PathVariable gameId: Int,
        @Valid @RequestBody
        shots: FireShotsInputModel,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<FireShotsOutputModel> {
        val shotsModels = playersService
            .fireShots(
                token = token,
                gameId = gameId,
                unfiredShotsDTO = UnfiredShotsDTO(shots = shots.shots.map { it.toUnfiredShotDTO() })
            )
            .shots
            .map { FiredShotModel(firedShotDTO = it) }

        return SirenEntity(
            `class` = listOf("my-shots"),
            properties = FiredShotsModel(shots = shotsModels),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.myShots(gameId = gameId)
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }

    /**
     * Handles the request to get the shots of the opponent.
     *
     * @param gameId the id of the game
     * @param token the token of the user
     *
     * @return the shots of the opponent
     */
    @GetMapping(Uris.PLAYERS_OPPONENT_SHOTS)
    fun getOpponentShots(
        @PathVariable gameId: Int,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<GetOpponentShotsOutputModel> {
        val shots = playersService
            .getOpponentShots(token = token, gameId = gameId)
            .shots
            .map(::FiredShotModel)

        return SirenEntity(
            `class` = listOf("opponent-shots"),
            properties = FiredShotsModel(shots = shots),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.opponentShots(gameId = gameId)
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = Uris.gameById(gameId = gameId)
                )
            )
        )
    }
}
