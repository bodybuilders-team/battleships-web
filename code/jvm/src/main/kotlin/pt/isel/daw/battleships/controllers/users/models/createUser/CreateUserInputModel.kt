package pt.isel.daw.battleships.controllers.users.models.createUser

import pt.isel.daw.battleships.services.users.dtos.CreateUserRequestDTO

/**
 * Represents the input data for the create user operation.
 *
 * @property username the username of the user to be created
 * @property password the password of the user to be created
 */
data class CreateUserInputModel(
    val username: String,
    val password: String
) {

    /**
     * Converts this model to a service DTO.
     *
     * @return the service DTO
     */
    fun toCreateUserRequest(): CreateUserRequestDTO = CreateUserRequestDTO(username, password)
}
