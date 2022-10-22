package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserInputDTO
import kotlin.test.Test

class RegisterUserInputDTOTests {

    @Test
    fun `CreateUserRequestDTO creation is successful`() {
        RegisterUserInputDTO(
            username = "username",
            email = "email",
            password = "password"
        )
    }
}
