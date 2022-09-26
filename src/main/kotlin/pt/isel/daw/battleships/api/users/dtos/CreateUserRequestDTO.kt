package pt.isel.daw.battleships.api.users.dtos

import pt.isel.daw.battleships.services.users.CreateUserRequest

data class CreateUserRequestDTO(val username: String, val password: String) {
    fun toCreateUserRequest(): CreateUserRequest = CreateUserRequest(username, password)
}
