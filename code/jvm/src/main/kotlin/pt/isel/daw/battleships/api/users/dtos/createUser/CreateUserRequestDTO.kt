package pt.isel.daw.battleships.api.users.dtos.createUser

import pt.isel.daw.battleships.services.users.CreateUserRequest

/**
 * Represents the input data for the create user operation.
 *
 * @property username the username of the user to be created
 * @property password the password of the user to be created
 */
data class CreateUserRequestDTO(
    val username: String,
    val password: String
) {

    /**
     * Converts this DTO to a service request.
     *
     * @return the service request
     */
    fun toCreateUserRequest(): CreateUserRequest = CreateUserRequest(username, password)
}
