package pt.isel.daw.battleships.http.controllers.users.models.refreshToken

import pt.isel.daw.battleships.service.users.dtos.refreshToken.RefreshTokenOutputDTO

/**
 * Represents the Refresh Token Output Model.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
data class RefreshTokenOutputModel(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(refreshTokenOutputDTO: RefreshTokenOutputDTO) : this(
        accessToken = refreshTokenOutputDTO.accessToken,
        refreshToken = refreshTokenOutputDTO.refreshToken
    )
}
