package pt.isel.daw.battleships.services.users

import pt.isel.daw.battleships.database.model.User

data class UserResponse(val username: String, val points: Int) {
    constructor(user: User) : this(user.username, user.points)
}
