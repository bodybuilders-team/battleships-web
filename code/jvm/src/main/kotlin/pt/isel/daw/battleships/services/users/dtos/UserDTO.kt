package pt.isel.daw.battleships.services.users.dtos

import pt.isel.daw.battleships.database.model.User

/**
 * Represents a DTO for a user.
 *
 * @property username the username of the user
 * @property points the points of the user
 */
data class UserDTO(
    val username: String,
    val points: Int
) {
    constructor(user: User) : this(user.username, user.points)
}
