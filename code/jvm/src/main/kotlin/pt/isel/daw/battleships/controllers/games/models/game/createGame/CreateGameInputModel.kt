package pt.isel.daw.battleships.controllers.games.models.game.createGame

import pt.isel.daw.battleships.controllers.games.models.game.GameConfigModel
import pt.isel.daw.battleships.services.games.dtos.game.CreateGameDTO

/**
 * Represents the input data for the create game operation.
 *
 * @property name the name of the game to be created
 * @property config the configuration of the game to be created
 */
data class CreateGameInputModel(
    val name: String,
    val config: GameConfigModel
) {

    /**
     * Converts this DTO to a service request.
     *
     * @return the service request
     */
    fun toCreateGameRequest(): CreateGameDTO {
        return CreateGameDTO(name, config.toGameConfigDTO())
    }
}
