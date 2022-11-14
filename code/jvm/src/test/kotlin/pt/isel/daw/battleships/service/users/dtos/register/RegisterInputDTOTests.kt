package pt.isel.daw.battleships.service.users.dtos.register

import kotlin.test.Test

class RegisterInputDTOTests {

    @Test
    fun `RegisterUserInputDTO creation is successful`() {
        RegisterInputDTO(
            username = "username",
            email = "email",
            password = "password"
        )
    }

    companion object {
        val defaultRegisterInputDTO
            get() = RegisterInputDTO(
                username = "username",
                email = "email",
                password = "password"
            )
    }
}
