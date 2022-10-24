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
        val loginUserInputModel = defaultLoginUserInputModel

        val loginUserInputDTO = loginUserInputModel.toLoginUserInputDTO()

        assertEquals(loginUserInputModel.username, loginUserInputDTO.username)
    }

    companion object {
        val defaultLoginUserInputModel
            get() = LoginUserInputModel(
                username = "username",
                password = "password"
            )
    }
}
