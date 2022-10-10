package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO

/**
 * Represents a game state.
 *
 * @property phase the current game phase
 * @property phaseEndTime the time when the current phase ends
 * @property round the current round
 * @property turn the current turn
 * @property winner the winner of the game
 */
data class GameStateModel(
    val phase: String,
    val phaseEndTime: Long,
    val round: Int?,
    val turn: String?,
    val winner: String?
) {
    constructor(stateDTO: GameStateDTO) : this(
        stateDTO.phase,
        stateDTO.phaseEndTime.time,
        stateDTO.round,
        stateDTO.turn,
        stateDTO.winner
    )
}
