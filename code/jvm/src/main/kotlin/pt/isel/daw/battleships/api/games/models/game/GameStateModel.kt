package pt.isel.daw.battleships.api.games.models.game

import pt.isel.daw.battleships.services.games.dtos.GameStateDTO

/**
 * Represents a game state.
 *
 * @property phase The current game phase.
 * @property round The current round.
 * @property turn The current turn.
 */
data class GameStateModel(
    val phase: String,
    val round: Int?,
    val turn: String?
) {
    constructor(state: GameStateDTO) : this(
        state.phase,
        state.round,
        state.turn
    )
}
