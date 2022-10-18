package pt.isel.daw.battleships.http.controllers.users.models

import pt.isel.daw.battleships.dtos.users.UserDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class UserModelTests {

    @Test
    fun `UserModel creation is successful`() {
        UserModel(
            username = "username",
            email = "email",
            points = 0
        )
    }

    @Test
    fun `UserModel from UserDTO conversion is successful`() {
        val userDTO = UserDTO(
            username = "username",
            email = "email",
            points = 0
        )
        val userModel = UserModel(userDTO)

        assertEquals(userDTO.username, userModel.username)
    }
}