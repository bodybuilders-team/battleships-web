package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.domain.users.User

/**
 * Represents a User DTO.
 *
 * @property username the username of the user
 * @property email the email of the user
 * @property points the points of the user
 * @property numberOfGamesPlayed the number of games played by the user
 */
data class UserDTO(
    val username: String,
    val email: String,
    val points: Int,
    val numberOfGamesPlayed: Int
) {
    constructor(user: User) : this(
        username = user.username,
        email = user.email,
        points = user.points,
        numberOfGamesPlayed = user.numberOfGamesPlayed
    )
}
