package pt.isel.daw.battleships.http.controllers.users.models

import pt.isel.daw.battleships.dtos.users.UserDTO
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * Represents a User model.
 *
 * @property username the username of the user
 * @property email the email of the user
 * @property points the points of the user
 */
data class UserModel(
    @Size(
        min = MIN_USERNAME_LENGTH,
        max = MAX_USERNAME_LENGTH,
        message = "Username must be between $MIN_USERNAME_LENGTH and $MAX_USERNAME_LENGTH characters long."
    )
    val username: String,

    @Size(
        min = MIN_EMAIL_LENGTH,
        max = MAX_EMAIL_LENGTH,
        message = "Email must be between $MIN_EMAIL_LENGTH and $MAX_EMAIL_LENGTH characters long."
    )
    @Pattern(regexp = EMAIL_REGEX)
    val email: String,

    @Min(value = MIN_POINTS.toLong(), message = "Points must be greater than or equal to $MIN_POINTS.")
    val points: Int
) {
    constructor(userDTO: UserDTO) : this(
        username = userDTO.username,
        email = userDTO.email,
        points = userDTO.points
    )

    companion object {
        const val MIN_USERNAME_LENGTH = 3
        const val MAX_USERNAME_LENGTH = 40

        const val MIN_EMAIL_LENGTH = 3
        const val MAX_EMAIL_LENGTH = 320
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)\$"

        const val MIN_POINTS = 0
    }
}
