package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.game.Game

data class GameDTO(
    val name: String,
    val config: GameConfigDTO,
    val state: GameStateDTO
) {
    constructor(game: Game) : this(game.name, GameConfigDTO(game.config), GameStateDTO(game.state))
}
