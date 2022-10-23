package pt.isel.daw.battleships.http.controllers.users.models.refreshToken

/**
 * Represents the Refresh Token Input Model.
 *
 * @property refreshToken the refresh token
 */
data class RefreshTokenInputModel(
    val refreshToken: String // TODO: Add validation
)
