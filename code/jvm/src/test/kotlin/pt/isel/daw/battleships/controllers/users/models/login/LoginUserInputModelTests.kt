package pt.isel.daw.battleships.controllers.users.models.login

import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUserInputModelTests {
    @Test
    fun `LoginUserInputModel creation is successful`() {
        LoginUserInputModel("username", "password")
    }

    @Test
    fun `LoginUserInputModel to LoginUserInputDTO conversion is successful`() {
        val loginUserInputModel = LoginUserInputModel("username", "password")
        val loginUserInputDTO = loginUserInputModel.toLoginUserInputDTO()

        assertEquals(loginUserInputModel.username, loginUserInputDTO.username)
    }
}
