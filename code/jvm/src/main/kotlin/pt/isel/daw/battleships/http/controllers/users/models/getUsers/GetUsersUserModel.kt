package pt.isel.daw.battleships.http.controllers.users.models.getUsers

import pt.isel.daw.battleships.service.users.dtos.UserDTO

/**
 * A Get Users User Model.
 *
 * @property username the username of the user
 * @property email the email of the user
 * @property points the points of the user
 * @property numberOfGamesPlayed the number of games played by the user
 */
data class GetUsersUserModel(
    val username: String,
    val email: String,
    val points: Int,
    val numberOfGamesPlayed: Int
) {
    constructor(userDTO: UserDTO) : this(
        username = userDTO.username,
        email = userDTO.email,
        points = userDTO.points,
        numberOfGamesPlayed = userDTO.numberOfGamesPlayed
    )
}
