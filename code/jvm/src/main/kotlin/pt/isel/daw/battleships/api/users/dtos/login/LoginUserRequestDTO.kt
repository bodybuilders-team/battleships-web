package pt.isel.daw.battleships.api.users.dtos.login

/**
 * Represents the data that is required to log in a user.
 *
 * @property username The username of the user.
 * @property password The password of the user.
 */
data class LoginUserRequestDTO(
    val username: String,
    val password: String
)
