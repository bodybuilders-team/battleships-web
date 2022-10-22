package pt.isel.daw.battleships.service.users.dtos.login

/**
 * Represents a Login User Input DTO.
 *
 * @property username name of the user
 * @property password password of the user
 */
data class LoginUserInputDTO(
    val username: String,
    val password: String
)
