package pt.isel.daw.battleships.http.controllers.users.models.register

import pt.isel.daw.battleships.service.users.dtos.register.RegisterOutputDTOTests.Companion.defaultRegisterOutputDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterOutputModelTests {

    @Test
    fun `RegisterUserOutputModel creation is successful`() {
        RegisterOutputModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

    @Test
    fun `RegisterUserOutputModel from RegisterUserOutputDTO conversion is successful`() {
        val registerUserOutputDTO = defaultRegisterOutputDTO

        val registerOutputModel = RegisterOutputModel(registerUserOutputDTO)

        assertEquals(registerUserOutputDTO.accessToken, registerOutputModel.accessToken)
        assertEquals(registerUserOutputDTO.refreshToken, registerOutputModel.refreshToken)
    }

    companion object {
        val defaultRegisterOutputModel
            get() = RegisterOutputModel(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
    }
}
