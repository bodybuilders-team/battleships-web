package pt.isel.daw.battleships.api.users.dtos

import pt.isel.daw.battleships.services.users.dtos.UserDTO

/**
 * Represents a User DTO.
 *
 * @property username The username of the user.
 * @property points The points of the user.
 */
data class UserModel(
    val username: String,
    val points: Int
) {
    constructor(userResponse: UserDTO) : this(userResponse.username, userResponse.points)
}
