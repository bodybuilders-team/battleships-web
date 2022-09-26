package pt.isel.daw.battleships.api.users.dtos

import pt.isel.daw.battleships.services.users.UserResponse

data class UserDTO(val username: String, val points: Int) {
    constructor(userResponse: UserResponse) : this(userResponse.username, userResponse.points)
}
