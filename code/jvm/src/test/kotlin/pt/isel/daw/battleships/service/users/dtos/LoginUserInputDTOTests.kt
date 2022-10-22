package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.service.users.dtos.login.LoginUserInputDTO
import kotlin.test.Test

class LoginUserInputDTOTests {

    @Test
    fun `LoginUserInputDTO creation is successful`() {
        LoginUserInputDTO(
            username = "username",
            password = "password"
        )
    }
}
