package pt.isel.daw.battleships.services.users.dtos.refresh

/**
 * Represents a Refresh Token Output DTO.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class RefreshTokenOutputDTO(
    val accessToken: String,
    val refreshToken: String
)
