package pt.isel.daw.battleships.http.controllers.users.models.createUser

import kotlin.test.Test
import kotlin.test.assertEquals

class CreateUserInputModelTests {

    @Test
    fun `CreateUserInputModel creation is successful`() {
        CreateUserInputModel(
            username = "username",
            email = "email",
            password = "password"
        )
    }

    @Test
    fun `CreateUserInputModel to CreateUserRequestDTO conversion is successful`() {
        val createUserInputModel = CreateUserInputModel(
            username = "username",
            email = "email",
            password = "password"
        )
        val createUserRequestDTO = createUserInputModel.toCreateUserRequestDTO()

        assertEquals(createUserInputModel.username, createUserRequestDTO.username)
        assertEquals(createUserInputModel.email, createUserRequestDTO.email)
        assertEquals(createUserInputModel.password, createUserRequestDTO.password)
    }
}
