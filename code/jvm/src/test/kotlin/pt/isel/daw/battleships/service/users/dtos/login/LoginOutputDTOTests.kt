package pt.isel.daw.battleships.service.users.dtos.login

import kotlin.test.Test

class LoginOutputDTOTests {

    @Test
    fun `LoginUserOutputDTO creation is successful`() {
        LoginOutputDTO(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultLoginOutputDTO
            get() = LoginOutputDTO(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
