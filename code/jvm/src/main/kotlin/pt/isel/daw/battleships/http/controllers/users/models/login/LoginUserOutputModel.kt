package pt.isel.daw.battleships.http.controllers.users.models.login

import pt.isel.daw.battleships.dtos.users.login.LoginUserOutputDTO

/**
 * Represents the response of a login request.
 *
 */
data class LoginUserOutputModel(val accessToken: String, val refreshToken: String) {
    constructor(token: LoginUserOutputDTO) :
        this(token.accessToken, token.refreshToken)
}
