package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.database.model.Game
import pt.isel.daw.battleships.services.games.GameResponse

/**
 * Represents a game.
 *
 * @property sessionName The name of the session.
 * @property config The configuration of the game.
 */
data class GameDTO(
    val sessionName: String,
    val config: GameConfigDTO
) {
    constructor(gameResponse: GameResponse) : this(TODO())

    constructor(game: Game) : this(TODO())
}
