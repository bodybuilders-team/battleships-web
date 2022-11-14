package pt.isel.daw.battleships.service.users.dtos.register

/**
 * A Register Output DTO.
 *
 * @property username name of the user
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class RegisterOutputDTO(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
