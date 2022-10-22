package pt.isel.daw.battleships.http.controllers.users.models

import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel
import pt.isel.daw.battleships.service.users.dtos.UserDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserOutputModelTests {

    @Test
    fun `UserModel creation is successful`() {
        GetUserOutputModel(
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
        val getUserOutputModel = GetUserOutputModel(userDTO)

        assertEquals(userDTO.username, getUserOutputModel.username)
    }
}
