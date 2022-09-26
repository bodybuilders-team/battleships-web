package pt.isel.daw.battleships.api.games.dtos.game

/**
 * Represents a game state.
 *
 * @property gamePhase The current game phase.
 * @property turn The current turn.
 * @property round The current round.
 */
data class GameStatusDTO(
    val gamePhase: String,
    val turn: String,
    val round: Int?
)
