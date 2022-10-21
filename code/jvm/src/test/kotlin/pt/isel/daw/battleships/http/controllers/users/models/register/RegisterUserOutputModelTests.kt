package pt.isel.daw.battleships.http.controllers.users.models.register

import kotlin.test.Test

class RegisterUserOutputModelTests {

    @Test
    fun `CreateUserOutputModel creation is successful`() {
        RegisterUserOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }
}
