package pt.isel.daw.battleships.controllers.games

import org.springframework.web.bind.annotation.*
import pt.isel.daw.battleships.controllers.games.models.ship.InputShipModel
import pt.isel.daw.battleships.controllers.games.models.ship.OutputShipModel
import pt.isel.daw.battleships.controllers.games.models.ship.OutputShipsModel
import pt.isel.daw.battleships.controllers.games.models.shot.CreateShotsInputModel
import pt.isel.daw.battleships.controllers.games.models.shot.OutputShotModel
import pt.isel.daw.battleships.controllers.games.models.shot.OutputShotsModel
import pt.isel.daw.battleships.services.games.PlayersService

/**
 * The controller that handles the requests to the game's player's resources.
 */
@RestController
@RequestMapping("/games/{gameId}/players")
class PlayersController(private val playersService: PlayersService) {

    /**
     * Gets the ships of the player.
     *
     * @param gameId The id of the game
     * @return The ships of the player
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
     * Gets the ships of the opponent.
     *
     * @param gameId The id of the game
     * @return The ships of the opponent
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
     * Deploys the ships of the player.
     *
     * @param gameId The id of the game
     * @param fleet The ships to be deployed
     */
    @PostMapping("/self/fleet")
    fun deployFleet(
        @PathVariable gameId: Int,
        @RequestBody fleet: List<InputShipModel>,
        @RequestAttribute("token") token: String
    ) {
        playersService.deployFleet(token, gameId, fleet.map { it.toDTO() })
    }

    /**
     * Gets the shots of the player.
     *
     * @param gameId The id of the game
     * @return The shots of the player
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
     * Gets the shots of the opponent.
     *
     * @param gameId The id of the game
     * @return The shots of the opponent
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
     * Creates the shots of the player.
     *
     * @param gameId The id of the game
     * @param shots The shots to be created
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
