package pt.isel.daw.battleships.services.users.dtos

/**
 * Represents a user creation request.
 *
 * @property username name of the user
 * @property password password of the user
 */
data class CreateUserRequestDTO(
    val username: String,
    val password: String
)
