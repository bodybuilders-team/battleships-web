package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.database.model.game.Game

/**
 * Represents a game.
 *
 * @property name The name of the session.
 * @property config The configuration of the game.
 * @property state The state of the game.
 */
data class GameDTO(
    val name: String,
    val config: GameConfigDTO,
    val state: GameStateDTO
) {
    constructor(game: Game) : this(
        game.name,
        GameConfigDTO(game.config),
        GameStateDTO(game.state)
    )
}
