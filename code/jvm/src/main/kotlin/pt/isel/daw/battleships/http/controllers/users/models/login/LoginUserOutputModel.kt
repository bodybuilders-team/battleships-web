package pt.isel.daw.battleships.http.controllers.users.models.login

import pt.isel.daw.battleships.service.users.dtos.login.LoginUserOutputDTO

/**
 * Represents a Login User Output Model.
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
