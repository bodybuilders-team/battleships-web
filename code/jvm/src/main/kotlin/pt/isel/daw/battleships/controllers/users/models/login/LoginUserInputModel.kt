package pt.isel.daw.battleships.controllers.users.models.login

/**
 * Represents the data that is required to log in a user.
 *
 * @property username the username of the user
 * @property password the password of the user
 */
data class LoginUserInputModel(
    val username: String,
    val password: String
)
