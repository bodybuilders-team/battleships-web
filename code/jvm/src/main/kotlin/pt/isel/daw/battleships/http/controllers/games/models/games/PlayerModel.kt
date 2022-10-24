package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.http.controllers.users.models.register.RegisterUserInputModel.Companion.MAX_USERNAME_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.register.RegisterUserInputModel.Companion.MIN_USERNAME_LENGTH
import pt.isel.daw.battleships.service.games.dtos.PlayerDTO
import javax.validation.constraints.Min
import javax.validation.constraints.Size

/**
 * Represents a Player Model.
 *
 * @property username the player name
 * @property points the player points
 */
data class PlayerModel(
    @field:Size(
        min = MIN_USERNAME_LENGTH,
        max = MAX_USERNAME_LENGTH,
        message = "Username must be between $MIN_USERNAME_LENGTH and $MAX_USERNAME_LENGTH characters long."
    )
    val username: String,

    @field:Min(value = MIN_POINTS.toLong(), message = "Points must be greater than or equal to $MIN_POINTS.")
    val points: Int
) {
    constructor(playerDTO: PlayerDTO) : this(
        username = playerDTO.username,
        points = playerDTO.points
    )

    companion object {
        const val MIN_POINTS = 0
    }
}
