package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.domain.games.game.GameState

/**
 * A Game State DTO.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the round of the game
 * @property turn the turn of the game
 * @property winner the winner of the game
 * @property endCause the cause of the game ending
 */
data class GameStateDTO(
    val phase: String,
    val phaseEndTime: Long,
    val round: Int?,
    val turn: String?,
    val winner: String?,
    val endCause: String?
) {
    constructor(gameState: GameState) : this(
        phase = gameState.phase.name,
        phaseEndTime = gameState.phaseExpirationTime.time,
        round = gameState.round,
        turn = gameState.turn?.user?.username,
        winner = gameState.winner?.user?.username,
        endCause = gameState.endCause?.name
    )
}
