package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.games.models.PlayerModel
import pt.isel.daw.battleships.services.games.dtos.game.GameDTO

/**
 * Represents a game.
 *
 * @property id the id of the game
 * @property name the name of the session
 * @property creator the creator of the game
 * @property config the configuration of the game
 * @property state the state of the game
 * @property players the players of the game
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
