package pt.isel.daw.battleships.controllers.users.models

import pt.isel.daw.battleships.services.users.dtos.UserDTO

/**
 * Represents a User model.
 *
 * @property username the username of the user
 * @property email the email of the user
 * @property points the points of the user
 */
data class UserModel(
    val username: String,
    val email: String,
    val points: Int
) {
    constructor(userDTO: UserDTO) : this(userDTO.username, userDTO.email, userDTO.points)
}
