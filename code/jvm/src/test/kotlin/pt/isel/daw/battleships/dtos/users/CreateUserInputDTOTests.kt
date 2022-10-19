package pt.isel.daw.battleships.dtos.users

import pt.isel.daw.battleships.dtos.users.createUser.CreateUserInputDTO
import kotlin.test.Test

class CreateUserInputDTOTests {

    @Test
    fun `CreateUserRequestDTO creation is successful`() {
        CreateUserInputDTO(username = "username", email = "email", password = "password")
    }
}
