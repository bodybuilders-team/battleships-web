package pt.isel.daw.battleships.http.controllers.users.models.register

import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterUserInputModelTests {

    @Test
    fun `RegisterUserInputModel creation is successful`() {
        RegisterUserInputModel(
            username = "username",
            email = "email",
            password = "password"
        )
    }

    @Test
    fun `RegisterUserInputModel to RegisterUserInputDTO conversion is successful`() {
        val registerUserInputModel = defaultRegisterUserInputModel

        val registerUserInputDTO = registerUserInputModel.toRegisterUserInputDTO()

        assertEquals(registerUserInputModel.username, registerUserInputDTO.username)
        assertEquals(registerUserInputModel.email, registerUserInputDTO.email)
        assertEquals(registerUserInputModel.password, registerUserInputDTO.password)
    }

    companion object {
        val defaultRegisterUserInputModel
            get() = RegisterUserInputModel(
                username = "username",
                email = "email",
                password = "password"
            )
    }
}
