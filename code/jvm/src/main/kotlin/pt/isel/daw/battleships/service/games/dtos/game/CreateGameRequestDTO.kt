package pt.isel.daw.battleships.service.games.dtos.game

/**
 * Represents a Create Game Request DTO.
 *
 * @property name name of the game
 * @property config configuration of the game
 */
data class CreateGameRequestDTO(
    val name: String,
    val config: GameConfigDTO
)
