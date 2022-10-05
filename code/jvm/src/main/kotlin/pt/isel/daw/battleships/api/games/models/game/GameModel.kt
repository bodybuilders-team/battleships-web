package pt.isel.daw.battleships.api.games.models.game

import pt.isel.daw.battleships.services.games.dtos.GameDTO

/**
 * Represents a game.
 *
 * @property name The name of the session.
 * @property config The configuration of the game.
 * @property state The state of the game.
 */
data class GameModel(
    val id: Int,
    val name: String,
    val creator: String,
    val config: GameConfigModel,
    val state: GameStateModel,
    val players: List<PlayerModel>
) {
    constructor(game: GameDTO) : this(
        game.id,
        game.name,
        game.creator,
        GameConfigModel(game.config),
        GameStateModel(game.state),
        game.players.map { PlayerModel(it) }
    )
}
