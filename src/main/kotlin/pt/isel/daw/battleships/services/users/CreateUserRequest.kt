package pt.isel.daw.battleships.services.users

/**
 * Represents a user creation request.
 *
 * @property username name of the user
 * @property password password of the user
 */
data class CreateUserRequest(
    val username: String,
    val password: String
)
