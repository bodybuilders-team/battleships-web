package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.services.games.GameResponse
import pt.isel.daw.battleships.services.games.GameStateResponse

/**
 * Represents a game state.
 *
 * @property phase The current game phase.
 * @property round The current round.
 * @property turn The current turn.
 */
data class GameStateDTO(
    val phase: String,
    val round: Int?,
    val turn: String?
) {
    constructor(gameStateResponse: GameStateResponse) : this(
        gameStateResponse.phase,
        gameStateResponse.round,
        gameStateResponse.turn
    )

    constructor(game: GameResponse) : this(
        game.phase,
        game.round,
        game.turn
    )
}
