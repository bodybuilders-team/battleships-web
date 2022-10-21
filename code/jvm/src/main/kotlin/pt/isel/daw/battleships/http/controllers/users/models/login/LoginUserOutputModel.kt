package pt.isel.daw.battleships.http.controllers.users.models.login

import pt.isel.daw.battleships.dtos.users.login.LoginUserOutputDTO

/**
 * Represents the response of a login request.
 *
 * @property accessToken the access token of the user
 * @property refreshToken the refresh token of the user
 */
data class LoginUserOutputModel(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(loginUserOutputDTO: LoginUserOutputDTO) : this(
        accessToken = loginUserOutputDTO.accessToken,
        refreshToken = loginUserOutputDTO.refreshToken
    )
}
