package pt.isel.daw.battleships.api.games

import jdk.jshell.spi.ExecutionControl.NotImplementedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.api.games.dtos.game.CreateGameResponseDTO
import pt.isel.daw.battleships.api.games.dtos.game.GameDTO
import pt.isel.daw.battleships.api.games.dtos.game.GameStatusDTO
import pt.isel.daw.battleships.utils.JwtUtils.JwtPayload

/**
 * The controller that handles the requests to the /games endpoint.
 */
@RestController
@RequestMapping("/games")
class GamesController {

    /**
     * Gets the list of games.
     *
     * @return the list of games.
     */
    @GetMapping
    fun getGames(): List<GameDTO> {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Creates a new game.
     *
     * @param game the game to be created.
     * @param authPayload the payload of the authentication token.
     *
     * @return the created game.
     */
    @PostMapping
    fun createGame(
        @RequestBody game: GameDTO,
        @RequestAttribute("authPayload") authPayload: JwtPayload
    ): CreateGameResponseDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Gets the game with the given id.
     *
     * @param gameId the id of the game to be retrieved.
     * @return the game with the given id.
     */
    @GetMapping("/{gameId}")
    fun getGame(
        @PathVariable gameId: Int
    ): GameDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Gets the status of the game with the given id.
     *
     * @param gameId the id of the game to be retrieved.
     * @return the status of the game with the given id.
     */
    @GetMapping("/{gameId}/status")
    fun getGameStatus(
        @PathVariable gameId: Int
    ): GameStatusDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }

    /**
     * Joins the game with the given id.
     *
     * @param gameId the id of the game to be joined.
     * @return the joined game.
     */
    @PostMapping("/{gameId}/join")
    fun joinGame(
        @PathVariable gameId: Int
    ): GameDTO {
        // TODO: To be implemented
        throw NotImplementedException("Not implemented")
    }
}
