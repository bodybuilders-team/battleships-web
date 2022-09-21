package pt.isel.daw.battleships.api.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.api.games.models.GameInputModel
import pt.isel.daw.battleships.database.model.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.model.shot.Shot

@RestController
@RequestMapping("/games")
class GamesController {

    @GetMapping("/")
    fun getGames(): List<Game> {
        // TODO: To be implemented
    }

    @PostMapping("/")
    fun createGame(
        @RequestBody game: GameInputModel
    ) {
        // TODO: To be implemented
    }

    @GetMapping("/{id}")
    fun getGame(
        @PathVariable id: Int
    ): Game {
        // TODO: To be implemented
    }

    @PostMapping("/{id}/join")
    fun joinGame(
        @PathVariable id: Int
    ) { // Return?
        // TODO: To be implemented
    }

    @GetMapping("/{id}/fleet")
    fun getFleet(
        @PathVariable id: Int
    ): List<Ship> {
        // TODO: To be implemented
    }

    @PostMapping("/{id}/fleet")
    fun createFleet(
        @PathVariable id: Int,
        @RequestBody fleet: List<Ship>
    ) {
        // TODO: To be implemented
    }

    @GetMapping("/{id}/opponent/fleet")
    fun getOpponentFleet(
        @PathVariable id: Int
    ): List<Ship> {
        // TODO: To be implemented
    }

    @GetMapping("/{id}/shots")
    fun getShots(
        @PathVariable id: Int
    ): List<Shot> {
        // TODO: To be implemented
    }

    @PostMapping("/{id}/shots")
    fun createShot(
        @PathVariable id: Int,
        @RequestBody shots: List<Shot>
    ) {
        // TODO: To be implemented
    }

    @GetMapping("/{id}/opponent/shots/latest")
    fun getLatestOpponentShots(
        @PathVariable id: Int
    ): List<Ship> {
        // TODO: To be implemented
    }
}
