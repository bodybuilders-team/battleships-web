package pt.isel.daw.battleships.controllers.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.controllers.games.models.ship.DeployFleetInputModel
import pt.isel.daw.battleships.controllers.games.models.ship.OutputShipModel
import pt.isel.daw.battleships.controllers.games.models.ship.OutputShipsModel
import pt.isel.daw.battleships.controllers.games.models.shot.CreateShotsInputModel
import pt.isel.daw.battleships.controllers.games.models.shot.OutputShotModel
import pt.isel.daw.battleships.controllers.games.models.shot.OutputShotsModel
import pt.isel.daw.battleships.services.games.PlayersService

/**
 * Controller that handles the requests to the game's player's resources.
 *
 * @property playersService the service that handles the requests to the game's player's resources
 */
@RestController
@RequestMapping("/games/{gameId}/players")
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
        @RequestAttribute("token") token: String
    ): OutputShipsModel =
        OutputShipsModel(
            playersService.getFleet(token, gameId)
                .map { OutputShipModel(it) }
        )

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
        @RequestAttribute("token") token: String
    ): OutputShipsModel =
        OutputShipsModel(
            playersService.getOpponentFleet(token, gameId)
                .map { OutputShipModel(it) }
        )

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
        @RequestAttribute("token") token: String
    ) {
        playersService.deployFleet(token, gameId, fleet.ships.map { it.toInputShipDTO() })
    }

    /**
     * Handles the request to get the shots of the player.
     *
     * @param gameId The id of the game
     * @param token the token of the user
     *
     * @return te shots of the player
     */
    @GetMapping("/self/shots")
    fun getShots(
        @PathVariable gameId: Int,
        @RequestAttribute("token") token: String
    ): OutputShotsModel =
        OutputShotsModel(
            playersService.getShots(token, gameId)
                .map { OutputShotModel(it) }
        )

    /**
     * Handles the request to get the shots of the opponent.
     *
     * @param gameId The id of the game
     * @param token the token of the user
     *
     * @return the shots of the opponent
     */
    @GetMapping("/opponent/shots")
    fun getOpponentShots(
        @PathVariable gameId: Int,
        @RequestAttribute("token") token: String
    ): OutputShotsModel =
        OutputShotsModel(
            playersService.getOpponentShots(token, gameId)
                .map { OutputShotModel(it) }
        )

    /**
     * Handles the request to create the shots of the player.
     *
     * @param gameId The id of the game
     * @param shots the shots to be created
     * @param token the token of the user
     *
     * @return the shots created
     */
    @PostMapping("/self/shots")
    fun createShots(
        @PathVariable gameId: Int,
        @RequestBody shots: CreateShotsInputModel,
        @RequestAttribute("token") token: String
    ): OutputShotsModel =
        OutputShotsModel(
            playersService.createShots(token, gameId, shots.shots.map { it.toInputShotDTO() })
                .map { OutputShotModel(it) }
        )
}
