package pt.isel.daw.battleships.service.users.dtos.login

import kotlin.test.Test

class LoginInputDTOTests {

    @Test
    fun `LoginUserInputDTO creation is successful`() {
        LoginInputDTO(
            username = "username",
            password = "password"
        )
    }

    companion object {
        val defaultLoginInputDTO
            get() = LoginInputDTO(
                username = "username",
                password = "password"
            )
    }
}
