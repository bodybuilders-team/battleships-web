package pt.isel.daw.battleships.http.controllers.users.models.login

import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUserInputModelTests {

    @Test
    fun `LoginUserInputModel creation is successful`() {
        LoginUserInputModel(
            username = "username",
            password = "password"
        )
    }

    @Test
    fun `LoginUserInputModel to LoginUserInputDTO conversion is successful`() {
        val loginUserInputModel = LoginUserInputModel(
            username = "username",
            password = "password"
        )
        val loginUserInputDTO = loginUserInputModel.toLoginUserInputDTO()

        assertEquals(loginUserInputModel.username, loginUserInputDTO.username)
    }
}
