package pt.isel.daw.battleships.http.controllers.users.models.login

import pt.isel.daw.battleships.service.users.dtos.login.LoginOutputDTOTests.Companion.defaultLoginOutputDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginOutputModelTests {

    @Test
    fun `LoginUserOutputModel creation is successful`() {
        LoginOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    @Test
    fun `LoginUserOutputModel from LoginUserOutputDTO conversion is successful`() {
        val loginUserOutputDTO = defaultLoginOutputDTO

        val loginOutputModel = LoginOutputModel(loginUserOutputDTO)

        assertEquals(loginUserOutputDTO.accessToken, loginOutputModel.accessToken)
        assertEquals(loginUserOutputDTO.refreshToken, loginOutputModel.refreshToken)
    }

    companion object {
        val defaultLoginOutputModel
            get() = LoginOutputModel(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
