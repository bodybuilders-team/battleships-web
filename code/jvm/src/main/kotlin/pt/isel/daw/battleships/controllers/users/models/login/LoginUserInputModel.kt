package pt.isel.daw.battleships.controllers.users.models.login

import pt.isel.daw.battleships.services.users.dtos.LoginUserInputDTO

/**
 * Represents the data that is required to log in a user.
 *
 * @property username the username of the user
 * @property password the password of the user
 */
data class LoginUserInputModel(
    val username: String,
    val password: String
) {
    /**
     * Converts this model to a service DTO.
     *
     * @return the service DTO
     */
    fun toLoginUserInputDTO(): LoginUserInputDTO = LoginUserInputDTO(username, password)
}
