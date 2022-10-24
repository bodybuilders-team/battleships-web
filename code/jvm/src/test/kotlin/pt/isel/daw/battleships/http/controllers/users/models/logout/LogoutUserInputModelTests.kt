package pt.isel.daw.battleships.http.controllers.users.models.logout

import kotlin.test.Test

class LogoutUserInputModelTests {

    @Test
    fun `LogoutUserInputModel creation is successful`() {
        LogoutUserInputModel(
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultLogoutUserInputModel
            get() = LogoutUserInputModel(
                refreshToken = "refreshToken"
            )
    }
}