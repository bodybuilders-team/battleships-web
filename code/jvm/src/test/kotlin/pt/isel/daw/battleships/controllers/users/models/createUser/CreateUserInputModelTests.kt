package pt.isel.daw.battleships.controllers.users.models.createUser

import kotlin.test.Test
import kotlin.test.assertEquals

class CreateUserInputModelTests {
    @Test
    fun `CreateUserInputModel creation is successful`() {
        CreateUserInputModel("username", "email", "password")
    }

    @Test
    fun `CreateUserInputModel to CreateUserRequestDTO conversion is successful`() {
        val createUserInputModel = CreateUserInputModel("username", "email", "password")
        val createUserRequestDTO = createUserInputModel.toCreateUserRequestDTO()

        assertEquals(createUserInputModel.username, createUserRequestDTO.username)
        assertEquals(createUserInputModel.email, createUserRequestDTO.email)
        assertEquals(createUserInputModel.password, createUserRequestDTO.password)
    }
}
