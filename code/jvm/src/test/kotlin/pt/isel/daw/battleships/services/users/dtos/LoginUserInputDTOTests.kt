package pt.isel.daw.battleships.services.users.dtos

import kotlin.test.Test

class LoginUserInputDTOTests {
    @Test
    fun `LoginUserInputDTO creation is successful`() {
        LoginUserInputDTO("username", "password")
    }
}
