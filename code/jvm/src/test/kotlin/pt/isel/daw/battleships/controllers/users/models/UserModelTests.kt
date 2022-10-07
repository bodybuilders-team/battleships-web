package pt.isel.daw.battleships.controllers.users.models

import pt.isel.daw.battleships.services.users.dtos.UserDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class UserModelTests {
    @Test
    fun `UserModel creation is successful`() {
        UserModel("username", "email", 0)
    }

    @Test
    fun `UserModel from UserDTO conversion is successful`() {
        val userDTO = UserDTO("username", "email", 0)
        val userModel = UserModel(userDTO)

        assertEquals(userDTO.username, userModel.username)
    }
}