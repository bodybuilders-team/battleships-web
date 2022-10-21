package pt.isel.daw.battleships.http.controllers.users.models.register

import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterUserInputModelTests {

    @Test
    fun `CreateUserInputModel creation is successful`() {
        RegisterUserInputModel(
            username = "username",
            email = "email",
            password = "password"
        )
    }

    @Test
    fun `CreateUserInputModel to CreateUserRequestDTO conversion is successful`() {
        val registerUserInputModel = RegisterUserInputModel(
            username = "username",
            email = "email",
            password = "password"
        )
        val createUserRequestDTO = registerUserInputModel.toRegisterDTO()

        assertEquals(registerUserInputModel.username, createUserRequestDTO.username)
        assertEquals(registerUserInputModel.email, createUserRequestDTO.email)
        assertEquals(registerUserInputModel.password, createUserRequestDTO.password)
    }
}
