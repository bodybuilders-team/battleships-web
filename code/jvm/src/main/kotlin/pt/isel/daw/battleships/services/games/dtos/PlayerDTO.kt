package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.domain.Player

/**
 * Represents a Player DTO.
 *
 * @property username the username of the player
 * @property points the points of the player
 */
data class PlayerDTO(
    val username: String,
    val points: Int
) {
    constructor(player: Player) : this(
        username = player.user.username,
        points = player.points
    )
}
