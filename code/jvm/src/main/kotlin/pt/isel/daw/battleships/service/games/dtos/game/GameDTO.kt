package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.service.games.dtos.PlayerDTO

/**
 * Represents a Game DTO.
 *
 * @property id the id of the game
 * @property name the name of the game
 * @property creator the creator of the game
 * @property config the configuration of the game
 * @property state the state of the game
 * @property players the players of the game
 */
data class GameDTO(
    val id: Int,
    val name: String,
    val creator: String,
    val config: GameConfigDTO,
    val state: GameStateDTO,
    val players: List<PlayerDTO>
) {
    constructor(game: Game) : this(
        id = game.id ?: throw IllegalStateException("Game id cannot be null"),
        name = game.name,
        creator = game.creator.username,
        config = GameConfigDTO(game.config),
        state = GameStateDTO(game.state),
        players = game.players.map { PlayerDTO(it) }
    )
}
