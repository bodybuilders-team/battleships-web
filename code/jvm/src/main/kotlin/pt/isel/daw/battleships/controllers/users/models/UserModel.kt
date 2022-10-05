package pt.isel.daw.battleships.controllers.users.models

import pt.isel.daw.battleships.services.users.dtos.UserDTO

/**
 * Represents a User model.
 *
 * @property username the username of the user
 * @property points the points of the user
 */
data class UserModel(
    val username: String,
    val points: Int
) {
    constructor(userResponse: UserDTO) : this(userResponse.username, userResponse.points)
}
