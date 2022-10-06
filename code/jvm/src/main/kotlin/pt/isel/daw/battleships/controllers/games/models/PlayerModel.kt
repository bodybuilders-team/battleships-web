package pt.isel.daw.battleships.controllers.games.models

import pt.isel.daw.battleships.services.games.dtos.PlayerDTO

/**
 * Represents a player.
 *
 * @property username the player name
 * @property points the player points
 */
data class PlayerModel(
    val username: String,
    val points: Int
) {
    constructor(player: PlayerDTO) : this(player.username, player.points)
}
