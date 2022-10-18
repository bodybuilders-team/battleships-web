package pt.isel.daw.battleships.services.users.dtos

import pt.isel.daw.battleships.dtos.users.LoginUserInputDTO
import kotlin.test.Test

class LoginUserInputDTOTests {

    @Test
    fun `LoginUserInputDTO creation is successful`() {
        LoginUserInputDTO(username = "username", password = "password")
    }
}
