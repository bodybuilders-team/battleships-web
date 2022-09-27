package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.database.model.game.GameState

/**
 * Represents a game state.
 *
 * @property phase The current game phase.
 * @property round The current round.
 * @property turn The current turn.
 */
data class GameStateDTO(
    val phase: String,
    val round: Int?,
    val turn: String?
) {
    constructor(state: GameState) : this(
        state.phase.name,
        state.round,
        state.turn?.user?.username
    )
}
