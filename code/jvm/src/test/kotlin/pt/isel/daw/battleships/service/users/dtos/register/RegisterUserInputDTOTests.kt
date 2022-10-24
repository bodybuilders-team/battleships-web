package pt.isel.daw.battleships.service.users.dtos.register

import kotlin.test.Test

class RegisterUserInputDTOTests {

    @Test
    fun `RegisterUserInputDTO creation is successful`() {
        RegisterUserInputDTO(
            username = "username",
            email = "email",
            password = "password"
        )
    }

    companion object {
        val defaultRegisterUserInputDTO
            get() = RegisterUserInputDTO(
                username = "username",
                email = "email",
                password = "password"
            )
    }
}
