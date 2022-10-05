package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.Player

/**
 * Represents a Player DTO.
 *
 * @property username the username of the player
 * @property points the points of the player
 */
data class PlayerDTO(val username: String, val points: Int) {
    constructor(player: Player) : this(player.user.username, player.points)
}
