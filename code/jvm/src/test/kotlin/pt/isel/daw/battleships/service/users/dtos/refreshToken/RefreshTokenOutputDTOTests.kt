package pt.isel.daw.battleships.service.users.dtos.refreshToken

import kotlin.test.Test

class RefreshTokenOutputDTOTests {

    @Test
    fun `RefreshTokenOutputDTO creation is successful`() {
        RefreshTokenOutputDTO(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    companion object {
        val defaultRefreshTokenOutputDTO
            get() = RefreshTokenOutputDTO(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}