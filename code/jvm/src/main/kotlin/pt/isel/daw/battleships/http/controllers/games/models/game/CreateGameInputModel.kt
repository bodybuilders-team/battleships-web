package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.CreateGameRequestDTO
import pt.isel.daw.battleships.http.controllers.games.models.game.GameModel.Companion.MAX_NAME_LENGTH
import pt.isel.daw.battleships.http.controllers.games.models.game.GameModel.Companion.MIN_NAME_LENGTH
import javax.validation.constraints.Size

/**
 * Represents the input data for the create game operation.
 *
 * @property name the name of the game to be created
 * @property config the configuration of the game to be created
 */
data class CreateGameInputModel(
    @Size(
        min = MIN_NAME_LENGTH,
        max = MAX_NAME_LENGTH,
        message = "Name must be between $MIN_NAME_LENGTH and $MAX_NAME_LENGTH characters long."
    )
    val name: String,
    val config: GameConfigModel
) {

    /**
     * Converts the game creation request model to a DTO.
     *
     * @return the game creation request DTO
     */
    fun toCreateGameRequestDTO() = CreateGameRequestDTO(
        name = name,
        config = config.toGameConfigDTO()
    )
}
