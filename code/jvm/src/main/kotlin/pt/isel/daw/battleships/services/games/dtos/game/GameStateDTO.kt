package pt.isel.daw.battleships.services.games.dtos.game

import pt.isel.daw.battleships.database.model.game.GameState

data class GameStateDTO(
    val phase: String,
    val round: Int?,
    val turn: String?,
    val winner: String?
) {
    constructor(phase: GameState) : this(
        phase.phase.name,
        phase.round,
        phase.turn?.user?.username,
        phase.winner?.user?.username
    )
}
