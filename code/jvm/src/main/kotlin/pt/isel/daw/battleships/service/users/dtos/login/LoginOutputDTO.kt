package pt.isel.daw.battleships.service.users.dtos.login

/**
 * A Login Output DTO.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class LoginOutputDTO(
    val accessToken: String,
    val refreshToken: String
)
