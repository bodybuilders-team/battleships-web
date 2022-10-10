package pt.isel.daw.battleships.services.games.dtos.game

import pt.isel.daw.battleships.database.model.game.GameState
import java.sql.Timestamp

/**
 * Represents a Game State DTO.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the round of the game
 * @property turn the turn of the game
 * @property winner the winner of the game
 */
data class GameStateDTO(
    val phase: String,
    val phaseEndTime: Timestamp,
    val round: Int?,
    val turn: String?,
    val winner: String?
) {
    constructor(phase: GameState) : this(
        phase.phase.name,
        phase.phaseEndTime,
        phase.round,
        phase.turn?.user?.username,
        phase.winner?.user?.username
    )
}
