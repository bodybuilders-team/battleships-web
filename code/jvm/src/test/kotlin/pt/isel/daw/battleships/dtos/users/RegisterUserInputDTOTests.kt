package pt.isel.daw.battleships.dtos.users

import pt.isel.daw.battleships.dtos.users.register.RegisterUserInputDTO
import kotlin.test.Test

class RegisterUserInputDTOTests {

    @Test
    fun `CreateUserRequestDTO creation is successful`() {
        RegisterUserInputDTO(username = "username", email = "email", password = "password")
    }
}
