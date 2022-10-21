package pt.isel.daw.battleships.dtos.users.register

/**
 * Represents a Register User Input DTO.
 *
 * @property username name of the user
 * @property email email of the user
 * @property password password of the user
 */
data class RegisterUserInputDTO(
    val username: String,
    val email: String,
    val password: String
)
