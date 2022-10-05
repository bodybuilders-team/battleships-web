package pt.isel.daw.battleships.services.games.dtos.game

/**
 * Represents a DTO for a game creation request.
 *
 * @property name name of the game
 * @property config configuration of the game
 */
data class CreateGameRequestDTO(
    val name: String,
    val config: GameConfigDTO
)
