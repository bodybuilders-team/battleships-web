package pt.isel.daw.battleships.api.games.dtos.game.createGame

import pt.isel.daw.battleships.api.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.services.games.CreateGameRequest

/**
 * Represents the input data for the create game operation.
 *
 * @property name the name of the game to be created
 * @property config the configuration of the game to be created
 */
data class CreateGameRequestDTO(
    val name: String,
    val config: GameConfigDTO
) {

    /**
     * Converts this DTO to a service request.
     *
     * @return the service request
     */
    fun toCreateGameRequest(): CreateGameRequest {
        TODO("Not yet implemented")
    }
}
