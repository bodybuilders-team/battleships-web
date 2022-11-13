package pt.isel.daw.battleships.http.controllers.users.models.register

import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserOutputDTO

/**
 * A Register User Output Model.
 *
 * @property accessToken the access token of the user
 * @property refreshToken the refresh token of the user
 */
data class RegisterUserOutputModel(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(registerUserOutputDTO: RegisterUserOutputDTO) : this(
        accessToken = registerUserOutputDTO.accessToken,
        refreshToken = registerUserOutputDTO.refreshToken
    )
}
