package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.database.model.Game

/**
 * Represents a response with a game.
 */
data class GameResponse(
    val id: Int,
    val sessionName: String,
    val player1: String?,
    val player2: String?,
    val phase: String,
    val round: Int?,
    val turn: String?,
    val winner: String?
) {
    constructor(game: Game) : this(
        game.id,
        game.sessionName,
        game.player1?.user?.username,
        game.player2?.user?.username,
        game.phase.name,
        game.round,
        game.turn?.user?.username,
        game.winner?.user?.username
    )
}
