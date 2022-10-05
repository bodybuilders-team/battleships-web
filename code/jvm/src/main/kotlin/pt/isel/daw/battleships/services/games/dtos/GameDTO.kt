package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.game.Game

data class GameDTO(
    val id: Int,
    val name: String,
    val creator: String,
    val config: GameConfigDTO,
    val state: GameStateDTO,
    val players: List<PlayerDTO>
) {
    constructor(game: Game) : this(
        game.id!!,
        game.name,
        game.creator.username,
        GameConfigDTO(game.config),
        GameStateDTO(game.state),
        game.players.map { PlayerDTO(it) }
    )
}
