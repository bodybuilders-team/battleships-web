package pt.isel.daw.battleships.http.controllers.games.models

import pt.isel.daw.battleships.http.controllers.users.models.UserModel.Companion.MAX_USERNAME_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.UserModel.Companion.MIN_POINTS
import pt.isel.daw.battleships.http.controllers.users.models.UserModel.Companion.MIN_USERNAME_LENGTH
import pt.isel.daw.battleships.services.games.dtos.PlayerDTO
import javax.validation.constraints.Min
import javax.validation.constraints.Size

/**
 * Represents a player.
 *
 * @property username the player name
 * @property points the player points
 */
data class PlayerModel(
    @Size(
        min = MIN_USERNAME_LENGTH,
        max = MAX_USERNAME_LENGTH,
        message = "Username must be between $MIN_USERNAME_LENGTH and $MAX_USERNAME_LENGTH characters long."
    )
    val username: String,

    @Min(value = MIN_POINTS.toLong(), message = "Points must be greater than or equal to $MIN_POINTS.")
    val points: Int
) {
    constructor(player: PlayerDTO) : this(
        username = player.username,
        points = player.points
    )
}
