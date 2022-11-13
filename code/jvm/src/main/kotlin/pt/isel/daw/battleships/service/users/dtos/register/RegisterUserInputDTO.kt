package pt.isel.daw.battleships.service.users.dtos.register

/**
 * A Register User Input DTO.
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
