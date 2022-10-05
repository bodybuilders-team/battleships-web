package pt.isel.daw.battleships.services.games.dtos.game

/**
 * Represents a game creation request.
 */
data class CreateGameDTO(
    val name: String,
    val config: GameConfigDTO
)
