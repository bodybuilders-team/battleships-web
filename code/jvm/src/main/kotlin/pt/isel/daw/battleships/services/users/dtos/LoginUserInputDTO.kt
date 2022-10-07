package pt.isel.daw.battleships.services.users.dtos

/**
 * Represents a DTO for a user login request.
 *
 * @property username name of the user
 * @property password password of the user
 */
data class LoginUserInputDTO(
    val username: String,
    val password: String
)