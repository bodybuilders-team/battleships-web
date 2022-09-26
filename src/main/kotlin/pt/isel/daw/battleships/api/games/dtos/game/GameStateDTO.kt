package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.services.games.GameStateResponse

/**
 * Represents a game state.
 *
 * @property gamePhase The current game phase.
 * @property turn The current turn.
 * @property round The current round.
 */
data class GameStateDTO(
    val gamePhase: String,
    val turn: String,
    val round: Int?
) {
    constructor(gameStateResponse: GameStateResponse) : this(TODO())
}
