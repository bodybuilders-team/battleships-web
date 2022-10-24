package pt.isel.daw.battleships.service.users.dtos.register

import kotlin.test.Test

class RegisterUserOutputDTOTests {

    @Test
    fun `RegisterUserOutputDTO creation is successful`() {
        RegisterUserOutputDTO(
            username = "username",
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultRegisterUserOutputDTO
            get() = RegisterUserOutputDTO(
                username = "username",
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
