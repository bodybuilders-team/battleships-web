package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.database.model.Game

/**
 * Represents a response with a game state.
 *
 * @property phase the state of the game
 * @property round the current round
 * @property turn the current turn
 */
data class GameStateResponse(
    val phase: String,
    val round: Int?,
    val turn: String?
) {
    constructor(game: Game) : this(
        game.phase.name,
        game.round,
        game.turn?.user?.username
    )
}
