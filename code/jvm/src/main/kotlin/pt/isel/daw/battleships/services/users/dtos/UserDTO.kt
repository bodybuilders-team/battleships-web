package pt.isel.daw.battleships.services.users.dtos

import pt.isel.daw.battleships.domain.User

/**
 * Represents a DTO for a user.
 *
 * @property username the username of the user
 * @property email the email of the user
 * @property points the points of the user
 */
data class UserDTO(
    val username: String,
    val email: String,
    val points: Int
) {
    constructor(user: User) : this(
        username = user.username,
        email = user.email,
        points = user.points
    )
}
