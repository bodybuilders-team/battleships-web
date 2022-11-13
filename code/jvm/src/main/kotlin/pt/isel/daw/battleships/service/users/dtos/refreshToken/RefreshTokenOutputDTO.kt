package pt.isel.daw.battleships.service.users.dtos.refreshToken

/**
 * A Refresh Token Output DTO.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class RefreshTokenOutputDTO(
    val accessToken: String,
    val refreshToken: String
)
