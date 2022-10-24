package pt.isel.daw.battleships.http.controllers.users.models.login

import pt.isel.daw.battleships.service.users.dtos.login.LoginUserOutputDTOTests.Companion.defaultLoginUserOutputDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUserOutputModelTests {

    @Test
    fun `LoginUserOutputModel creation is successful`() {
        LoginUserOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    @Test
    fun `LoginUserOutputModel from LoginUserOutputDTO conversion is successful`() {
        val loginUserOutputDTO = defaultLoginUserOutputDTO

        val loginUserOutputModel = LoginUserOutputModel(loginUserOutputDTO)

        assertEquals(loginUserOutputDTO.accessToken, loginUserOutputModel.accessToken)
        assertEquals(loginUserOutputDTO.refreshToken, loginUserOutputModel.refreshToken)
    }

    companion object {
        val defaultLoginUserOutputModel
            get() = LoginUserOutputModel(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
