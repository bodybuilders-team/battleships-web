package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.game.GameState

data class GameStateDTO(
    val phase: String,

    val round: Int = 0,

    val turn: String
) {
    constructor(phase: GameState) : this(phase.phase.name, phase.round, phase.turn?.playerPk?.user?.username!!)
}
