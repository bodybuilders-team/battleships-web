package pt.isel.daw.battleships.service.users.dtos.login

import kotlin.test.Test

class LoginUserOutputDTOTests {

    @Test
    fun `LoginUserOutputDTO creation is successful`() {
        LoginUserOutputDTO(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultLoginUserOutputDTO
            get() = LoginUserOutputDTO(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}