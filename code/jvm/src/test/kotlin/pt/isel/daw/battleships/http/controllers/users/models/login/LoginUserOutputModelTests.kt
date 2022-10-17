package pt.isel.daw.battleships.http.controllers.users.models.login

import kotlin.test.Test

class LoginUserOutputModelTests {

    @Test
    fun `LoginUserOutputModel creation is successful`() {
        LoginUserOutputModel(token = "token")
    }
}
