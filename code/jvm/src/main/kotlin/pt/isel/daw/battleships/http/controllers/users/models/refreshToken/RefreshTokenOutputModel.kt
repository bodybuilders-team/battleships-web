package pt.isel.daw.battleships.http.controllers.users.models.refreshToken

import pt.isel.daw.battleships.dtos.users.refresh.RefreshTokenOutputDTO

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
    constructor(refreshTokenDTO: RefreshTokenOutputDTO) : this(
        accessToken = refreshTokenDTO.accessToken,
        refreshToken = refreshTokenDTO.refreshToken
    )
}
