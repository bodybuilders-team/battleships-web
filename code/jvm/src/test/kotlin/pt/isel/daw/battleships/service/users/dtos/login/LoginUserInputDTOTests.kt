package pt.isel.daw.battleships.service.users.dtos.login

import kotlin.test.Test

class LoginUserInputDTOTests {

    @Test
    fun `LoginUserInputDTO creation is successful`() {
        LoginUserInputDTO(
            username = "username",
            password = "password"
        )
    }

    companion object {
        val defaultLoginUserInputDTO
            get() = LoginUserInputDTO(
                username = "username",
                password = "password"
            )
    }
}
