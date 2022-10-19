package pt.isel.daw.battleships.http.controllers.users.models.createUser

import pt.isel.daw.battleships.dtos.users.createUser.CreateUserOutputDTO

/**
 * Represents the response body of the create user operation.
 *
 */
data class CreateUserOutputModel(val accessToken: String, val refreshToken: String) {
    constructor(token: CreateUserOutputDTO) :
        this(token.accessToken, token.refreshToken)
}
