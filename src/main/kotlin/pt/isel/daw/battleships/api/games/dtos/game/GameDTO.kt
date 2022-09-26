package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.services.games.GameResponse

/**
 * Represents a game.
 *
 * @property sessionName The name of the session.
 * @property config The configuration of the game.
 * @property state The state of the game.
 */
data class GameDTO(
    val sessionName: String,
    val config: GameConfigDTO,
    val state: GameStateDTO
) {
    constructor(game: GameResponse) : this(
        game.sessionName,
        GameConfigDTO(game),
        GameStateDTO(game)
    )
}
