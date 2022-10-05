package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO

/**
 * Represents a game state.
 *
 * @property phase the current game phase
 * @property round the current round
 * @property turn the current turn
 * @property winner the winner of the game
 */
data class GameStateModel(
    val phase: String,
    val round: Int?,
    val turn: String?,
    val winner: String?
) {
    constructor(state: GameStateDTO) : this(
        state.phase,
        state.round,
        state.turn,
        state.winner
    )
}
