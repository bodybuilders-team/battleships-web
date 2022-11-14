package pt.isel.daw.battleships.service.users.dtos.register

import kotlin.test.Test

class RegisterOutputDTOTests {

    @Test
    fun `RegisterUserOutputDTO creation is successful`() {
        RegisterOutputDTO(
            username = "username",
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultRegisterOutputDTO
            get() = RegisterOutputDTO(
                username = "username",
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
