package pt.isel.daw.battleships.http.controllers.users.models.login

import pt.isel.daw.battleships.dtos.users.LoginUserInputDTO
import pt.isel.daw.battleships.http.controllers.users.models.UserModel.Companion.MAX_USERNAME_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.UserModel.Companion.MIN_USERNAME_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.createUser.CreateUserInputModel.Companion.MAX_PASSWORD_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.createUser.CreateUserInputModel.Companion.MIN_PASSWORD_LENGTH
import javax.validation.constraints.Size

/**
 * Represents the data that is required to log in a user.
 *
 * @property username the username of the user
 * @property password the password of the user
 */
data class LoginUserInputModel(
    @Size(
        min = MIN_USERNAME_LENGTH,
        max = MAX_USERNAME_LENGTH,
        message = "Username must be between $MIN_USERNAME_LENGTH and $MAX_USERNAME_LENGTH characters long."
    )
    val username: String,

    @Size(
        min = MIN_PASSWORD_LENGTH,
        max = MAX_PASSWORD_LENGTH,
        message = "Password must be between $MIN_PASSWORD_LENGTH and $MAX_PASSWORD_LENGTH characters long."
    )
    val password: String
) {

    /**
     * Converts this model to a service DTO.
     *
     * @return the service DTO
     */
    fun toLoginUserInputDTO(): LoginUserInputDTO = LoginUserInputDTO(
        username = username,
        password = password
    )
}