package pt.isel.daw.battleships.service.users.dtos.login

/**
 * A Login User Output DTO.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class LoginUserOutputDTO(
    val accessToken: String,
    val refreshToken: String
)
