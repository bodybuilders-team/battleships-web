package pt.isel.daw.battleships.dtos.users

import pt.isel.daw.battleships.dtos.users.login.LoginUserInputDTO
import kotlin.test.Test

class LoginUserInputDTOTests {

    @Test
    fun `LoginUserInputDTO creation is successful`() {
        LoginUserInputDTO(username = "username", password = "password")
    }
}
