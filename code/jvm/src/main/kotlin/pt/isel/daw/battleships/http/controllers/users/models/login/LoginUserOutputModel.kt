package pt.isel.daw.battleships.http.controllers.users.models.login

/**
 * Represents the response of a login request.
 *
 * @property token the token that identifies the user
 */
data class LoginUserOutputModel(val token: String)
