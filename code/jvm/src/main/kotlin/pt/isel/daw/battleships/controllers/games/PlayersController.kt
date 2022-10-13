package pt.isel.daw.battleships.controllers.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.controllers.games.models.ship.DeployFleetInputModel
import pt.isel.daw.battleships.controllers.games.models.ship.DeployFleetOutputModel
import pt.isel.daw.battleships.controllers.games.models.ship.OutputShipModel
import pt.isel.daw.battleships.controllers.games.models.ship.OutputShipsModel
import pt.isel.daw.battleships.controllers.games.models.shot.CreateShotsInputModel
import pt.isel.daw.battleships.controllers.games.models.shot.OutputShotModel
import pt.isel.daw.battleships.controllers.games.models.shot.OutputShotsModel
import pt.isel.daw.battleships.controllers.utils.siren.EmbeddedLink
import pt.isel.daw.battleships.controllers.utils.siren.Link
import pt.isel.daw.battleships.controllers.utils.siren.SIREN_TYPE
import pt.isel.daw.battleships.controllers.utils.siren.SirenEntity
import pt.isel.daw.battleships.services.games.PlayersService
import pt.isel.daw.battleships.services.games.dtos.ship.InputFleetDTO
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotsDTO
import pt.isel.daw.battleships.utils.JwtProvider.Companion.TOKEN_ATTRIBUTE
import java.net.URI

/**
 * Controller that handles the requests to the game's player's resources.
 *
 * @property playersService the service that handles the requests to the game's player's resources
 */
@RestController
@RequestMapping("/games/{gameId}/players", produces = [SIREN_TYPE])
class PlayersController(private val playersService: PlayersService) {

    /**
     * Handles the request to get the fleet of a player.
     *
     * @param gameId the id of the game
     * @param token the token of the user
     *
     * @return the fleet of the player
     */
    @GetMapping("/self/fleet")
    fun getFleet(
        @PathVariable gameId: Int,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<OutputShipsModel> {
        val ships = playersService.getFleet(token, gameId).ships
            .map { OutputShipModel(it) }

        return SirenEntity(
            `class` = listOf("my-fleet"),
            properties = OutputShipsModel(ships),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId/players/self/fleet")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                )
            )
        )
    }

    /**
     * Handles the request to get the opponent's fleet.
     * Only gets those that are sunk.
     *
     * @param gameId the id of the game
     * @param token the token of the user
     *
     * @return The fleet of the opponent
     */
    @GetMapping("/opponent/fleet")
    fun getOpponentFleet(
        @PathVariable gameId: Int,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<OutputShipsModel> {
        val ships = playersService.getOpponentFleet(token, gameId).ships
            .map { OutputShipModel(it) }

        return SirenEntity(
            `class` = listOf("opponent-fleet"),
            properties = OutputShipsModel(ships),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId/players/opponent/fleet")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                )
            )
        )
    }

    /**
     * Handles the request to deploy a fleet.
     *
     * @param gameId the id of the game
     * @param fleet the ships to be deployed
     * @param token the token of the user
     */
    @PostMapping("/self/fleet")
    fun deployFleet(
        @PathVariable gameId: Int,
        @RequestBody fleet: DeployFleetInputModel,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<DeployFleetOutputModel> {
        playersService
            .deployFleet(
                token = token,
                gameId = gameId,
                fleetDTO = InputFleetDTO(fleet.ships.map { it.toInputShipDTO() })
            )

        return SirenEntity(
            properties = DeployFleetOutputModel(successfullyDeployed = true),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("fleet"),
                    href = URI("/games/$gameId/players/self/fleet")
                ),
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                )
            )
        )
    }

    /**
     * Handles the request to get the shots of the player.
     *
     * @param gameId the id of the game
     * @param token the token of the user
     *
     * @return te shots of the player
     */
    @GetMapping("/self/shots")
    fun getShots(
        @PathVariable gameId: Int,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<OutputShotsModel> {
        val shots = playersService.getShots(token, gameId).shots
            .map { OutputShotModel(it) }

        return SirenEntity(
            `class` = listOf("my-shots"),
            properties = OutputShotsModel(shots),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId/players/self/shots")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
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
    @GetMapping("/opponent/shots")
    fun getOpponentShots(
        @PathVariable gameId: Int,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<OutputShotsModel> {
        val shots = playersService.getOpponentShots(token, gameId).shots
            .map { OutputShotModel(it) }

        return SirenEntity(
            `class` = listOf("opponent-shots"),
            properties = OutputShotsModel(shots),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId/players/opponent/shots")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
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
    @PostMapping("/self/shots")
    fun shoot(
        @PathVariable gameId: Int,
        @RequestBody shots: CreateShotsInputModel,
        @RequestAttribute(TOKEN_ATTRIBUTE) token: String
    ): SirenEntity<OutputShotsModel> {
        val shotsModels = playersService
            .shoot(token, gameId, InputShotsDTO(shots.shots.map { it.toInputShotDTO() }))
            .shots
            .map { OutputShotModel(it) }

        return SirenEntity(
            `class` = listOf("my-shots"),
            properties = OutputShotsModel(shotsModels),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/games/$gameId/players/self/shots")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("game"),
                    href = URI("/games/$gameId")
                )
            )
        )
    }
}
