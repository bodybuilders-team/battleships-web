package pt.isel.daw.battleships.api.games.dtos.game

/**
 * Represents a game.
 *
 * @property sessionName The name of the session.
 * @property config The configuration of the game.
 */
data class GameDTO(
    val sessionName: String,
    val config: GameConfigDTO
)
