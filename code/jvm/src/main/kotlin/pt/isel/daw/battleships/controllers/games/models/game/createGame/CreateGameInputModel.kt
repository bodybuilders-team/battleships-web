package pt.isel.daw.battleships.controllers.games.models.game.createGame

import pt.isel.daw.battleships.controllers.games.models.game.GameConfigModel
import pt.isel.daw.battleships.services.games.dtos.game.CreateGameRequestDTO

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
     * Converts the game creation request model to a DTO.
     *
     * @return the game creation request DTO
     */
    fun toCreateGameRequestDTO() = CreateGameRequestDTO(name, config.toGameConfigDTO())
}
