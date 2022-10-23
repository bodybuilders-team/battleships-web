package pt.isel.daw.battleships.http.controllers.users.models.register

import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel.Companion.EMAIL_REGEX
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel.Companion.MAX_EMAIL_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel.Companion.MAX_USERNAME_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel.Companion.MIN_EMAIL_LENGTH
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel.Companion.MIN_USERNAME_LENGTH
import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserInputDTO
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * Represents a Register User Input Model.
 *
 * @property username the username of the user to be created
 * @property email the email of the user to be created
 * @property password the password of the user to be created
 */
data class RegisterUserInputModel(
    @Size(
        min = MIN_USERNAME_LENGTH,
        max = MAX_USERNAME_LENGTH,
        message = "Username must be between $MIN_USERNAME_LENGTH and $MAX_USERNAME_LENGTH characters long."
    )
    val username: String,

    @Size(
        min = MIN_EMAIL_LENGTH,
        max = MAX_EMAIL_LENGTH,
        message = "Email must be between $MIN_EMAIL_LENGTH and $MAX_EMAIL_LENGTH characters long."
    )
    @Pattern(regexp = EMAIL_REGEX)
    val email: String,

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
    fun toRegisterUserInputDTO() = RegisterUserInputDTO(
        username = username,
        email = email,
        password = password
    )

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 127
    }
}
