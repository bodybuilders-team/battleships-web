package pt.isel.daw.battleships.api.games

import jdk.jshell.spi.ExecutionControl.NotImplementedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.api.games.dtos.CreateShotsRequestDTO
import pt.isel.daw.battleships.api.games.dtos.ShipDTO
import pt.isel.daw.battleships.database.model.shot.Shot

@RestController
@RequestMapping("/games/{gameId}/players/self")
class PlayersController {

    @GetMapping("/self/fleet/")
    fun getFleet(
        @PathVariable gameId: Int
    ): List<ShipDTO> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @GetMapping("/opponent/fleet")
    fun getOpponentFleet(
        @PathVariable gameId: Int
    ): List<ShipDTO> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @PostMapping("/self/fleet")
    fun deployFleet(
        @PathVariable gameId: Int,
        @RequestBody fleet: List<ShipDTO>
    ) {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @GetMapping("/self/shots")
    fun getShots(
        @PathVariable gameId: Int
    ): List<Shot> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @GetMapping("/opponent/shots")
    fun getOpponentShots(
        @PathVariable gameId: Int
    ): List<Shot> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @PostMapping("/self/shots")
    fun createShots(
        @PathVariable gameId: Int,
        @RequestBody shots: CreateShotsRequestDTO
    ) {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }
}
