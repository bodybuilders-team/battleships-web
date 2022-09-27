package pt.isel.daw.battleships.api.games

import jdk.jshell.spi.ExecutionControl.NotImplementedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.api.games.dtos.ship.ShipDTO
import pt.isel.daw.battleships.api.games.dtos.shot.CreateShotsRequestDTO
import pt.isel.daw.battleships.database.model.shot.Shot

/**
 * The controller that handles the requests to the game's player's resources.
 */
@RestController
@RequestMapping("/games/{gameId}/players")
class PlayersController {

    /**
     * Gets the ships of the player.
     *
     * @param gameId The id of the game.
     * @return The ships of the player.
     */
    @GetMapping("/self/fleet")
    fun getFleet(
        @PathVariable gameId: Int
    ): List<ShipDTO> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Gets the ships of the opponent.
     *
     * @param gameId The id of the game.
     * @return The ships of the opponent.
     */
    @GetMapping("/opponent/fleet")
    fun getOpponentFleet(
        @PathVariable gameId: Int
    ): List<ShipDTO> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Deploys the ships of the player.
     *
     * @param gameId The id of the game.
     * @param fleet The ships to be deployed.
     */
    @PostMapping("/self/fleet")
    fun deployFleet(
        @PathVariable gameId: Int,
        @RequestBody fleet: List<ShipDTO>
    ) {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Gets the shots of the player.
     *
     * @param gameId The id of the game.
     * @return The shots of the player.
     */
    @GetMapping("/self/shots")
    fun getShots(
        @PathVariable gameId: Int
    ): List<Shot> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Gets the shots of the opponent.
     *
     * @param gameId The id of the game.
     * @return The shots of the opponent.
     */
    @GetMapping("/opponent/shots")
    fun getOpponentShots(
        @PathVariable gameId: Int
    ): List<Shot> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Creates the shots of the player.
     *
     * @param gameId The id of the game.
     * @param shots The shots to be created.
     */
    @PostMapping("/self/shots")
    fun createShots(
        @PathVariable gameId: Int,
        @RequestBody shots: CreateShotsRequestDTO
    ) {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }
}
