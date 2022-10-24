package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.service.games.dtos.PlayerDTO

/**
 * Represents a Player Model.
 *
 * @property username the player name
 * @property points the player points
 */
data class PlayerModel(
    val username: String,
    val points: Int
) {
    constructor(playerDTO: PlayerDTO) : this(
        username = playerDTO.username,
        points = playerDTO.points
    )
}
