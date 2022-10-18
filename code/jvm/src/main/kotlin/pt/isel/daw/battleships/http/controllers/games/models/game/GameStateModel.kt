package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.GameStateDTO
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

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
    @Pattern(regexp = PHASE_REGEX) // TODO: This is Output, so no validation needed?
    val phase: String,
    val phaseEndTime: Long,

    @Min(value = MIN_ROUND.toLong(), message = "The round must be greater than or equal to $MIN_ROUND.")
    val round: Int?,
    val turn: String?,
    val winner: String?
) {
    constructor(stateDTO: GameStateDTO) : this(
        phase = stateDTO.phase,
        phaseEndTime = stateDTO.phaseEndTime,
        round = stateDTO.round,
        turn = stateDTO.turn,
        winner = stateDTO.winner
    )

    companion object {
        private const val PHASE_REGEX = "WAITING_FOR_PLAYERS|GRID_LAYOUT|IN_PROGRESS|FINISHED"
        private const val MIN_ROUND = 1
    }
}
