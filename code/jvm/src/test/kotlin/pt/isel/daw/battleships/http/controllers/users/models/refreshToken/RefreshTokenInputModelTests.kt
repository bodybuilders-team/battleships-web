package pt.isel.daw.battleships.http.controllers.users.models.refreshToken

import kotlin.test.Test

class RefreshTokenInputModelTests {

    @Test
    fun `RefreshTokenInputModel creation is successful`() {
        RefreshTokenInputModel(
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultRefreshTokenInputModel
            get() = RefreshTokenInputModel(
                refreshToken = "refreshToken"
            )
    }
}
