package pt.isel.daw.battleships.http.controllers.users.models.createUser

import kotlin.test.Test

class CreateUserOutputModelTests {

    @Test
    fun `CreateUserOutputModel creation is successful`() {
        CreateUserOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }
}
