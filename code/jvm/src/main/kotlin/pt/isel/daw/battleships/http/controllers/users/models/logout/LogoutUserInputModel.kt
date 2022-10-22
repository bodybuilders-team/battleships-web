package pt.isel.daw.battleships.http.controllers.users.models.logout

/**
 * Represents a Logout Model.
 *
 * @property refreshToken the refresh token of the user
 */
data class LogoutUserInputModel(
    val refreshToken: String
)
