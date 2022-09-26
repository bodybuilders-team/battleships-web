package pt.isel.daw.battleships.api.games

import jdk.jshell.spi.ExecutionControl.NotImplementedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.JwtUtils.JwtPayload
import pt.isel.daw.battleships.api.games.dtos.CreateGameResponseDTO
import pt.isel.daw.battleships.api.games.dtos.GameDTO
import pt.isel.daw.battleships.api.games.dtos.GameStatusDTO

@RestController
@RequestMapping("/games")
class GamesController {

    @GetMapping
    fun getGames(): List<GameDTO> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @PostMapping
    fun createGame(
        @RequestBody game: GameDTO,
        @RequestAttribute("authPayload") authPayload: JwtPayload
    ): CreateGameResponseDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @GetMapping("/{gameId}")
    fun getGame(
        @PathVariable gameId: Int
    ): GameDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @GetMapping("/{gameId}/status")
    fun getGameStatus(
        @PathVariable gameId: Int
    ): GameStatusDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    @PostMapping("/{gameId}/join")
    fun joinGame(
        @PathVariable gameId: Int
    ): GameDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }
}
