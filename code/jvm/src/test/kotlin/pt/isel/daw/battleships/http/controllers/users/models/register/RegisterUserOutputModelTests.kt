package pt.isel.daw.battleships.http.controllers.users.models.register

import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserOutputDTOTests.Companion.defaultRegisterUserOutputDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterUserOutputModelTests {

    @Test
    fun `RegisterUserOutputModel creation is successful`() {
        RegisterUserOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    @Test
    fun `RegisterUserOutputModel from RegisterUserOutputDTO conversion is successful`() {
        val registerUserOutputDTO = defaultRegisterUserOutputDTO

        val registerUserOutputModel = RegisterUserOutputModel(registerUserOutputDTO)

        assertEquals(registerUserOutputDTO.accessToken, registerUserOutputModel.accessToken)
        assertEquals(registerUserOutputDTO.refreshToken, registerUserOutputModel.refreshToken)
    }

    companion object {
        val defaultRegisterUserOutputModel
            get() = RegisterUserOutputModel(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
