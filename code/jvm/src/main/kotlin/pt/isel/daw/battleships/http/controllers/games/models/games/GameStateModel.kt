package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTO

/**
 * A Game State Model.
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
    constructor(gameStateDTO: GameStateDTO) : this(
        phase = gameStateDTO.phase,
        phaseEndTime = gameStateDTO.phaseEndTime,
        round = gameStateDTO.round,
        turn = gameStateDTO.turn,
        winner = gameStateDTO.winner
    )
}
