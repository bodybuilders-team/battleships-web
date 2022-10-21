package pt.isel.daw.battleships.dtos.users.register

/**
 * Represents a Register User Output DTO.
 *
 * @property username name of the user
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class RegisterUserOutputDTO(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
