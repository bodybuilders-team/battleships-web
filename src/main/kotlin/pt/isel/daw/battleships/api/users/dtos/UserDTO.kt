package pt.isel.daw.battleships.api.users.dtos

import pt.isel.daw.battleships.services.users.UserResponse

/**
 * Represents a User DTO.
 *
 * @property username The username of the user.
 * @property points The points of the user.
 */
data class UserDTO(
    val username: String,
    val points: Int
) {
    constructor(userResponse: UserResponse) : this(userResponse.username, userResponse.points)
}
