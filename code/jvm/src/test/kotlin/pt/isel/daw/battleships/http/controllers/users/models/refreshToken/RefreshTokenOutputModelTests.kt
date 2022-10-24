package pt.isel.daw.battleships.http.controllers.users.models.refreshToken

import pt.isel.daw.battleships.service.users.dtos.refreshToken.RefreshTokenOutputDTOTests.Companion.defaultRefreshTokenOutputDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshTokenOutputModelTests {

    @Test
    fun `RefreshTokenOutputModel creation is successful`() {
        RefreshTokenOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    @Test
    fun `RefreshTokenOutputModel from RefreshTokenOutputDTO conversion is successful`() {
        val refreshTokenOutputDTO = defaultRefreshTokenOutputDTO

        val refreshTokenOutputModel = RefreshTokenOutputModel(refreshTokenOutputDTO)

        assertEquals(refreshTokenOutputDTO.accessToken, refreshTokenOutputModel.accessToken)
        assertEquals(refreshTokenOutputDTO.refreshToken, refreshTokenOutputModel.refreshToken)
    }

    companion object {
        val defaultRefreshTokenOutputModel
            get() = RefreshTokenOutputModel(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
