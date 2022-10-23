package pt.isel.daw.battleships.http.controllers.games.models.games.createGame

import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModel
import pt.isel.daw.battleships.service.games.dtos.game.CreateGameRequestDTO
import javax.validation.constraints.Size

/**
 * Represents a Create Game Input Model.
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

    companion object {
        const val MIN_NAME_LENGTH = 1
        const val MAX_NAME_LENGTH = 40
    }
}
