package pt.isel.daw.battleships.http.controllers.users.models.getUser

import pt.isel.daw.battleships.service.users.dtos.UserDTOTests.Companion.defaultUserDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserOutputModelTests {

    @Test
    fun `GetUserOutputModel creation is successful`() {
        GetUserOutputModel(
            username = "username",
            email = "email",
            points = 0,
            numberOfGamesPlayed = 0
        )
    }

    @Test
    fun `GetUserOutputModel from UserDTO conversion is successful`() {
        val userDTO = defaultUserDTO(0)

        val getUserOutputModel = GetUserOutputModel(userDTO)

        assertEquals(userDTO.username, getUserOutputModel.username)
    }

    companion object {
        val defaultGetUserOutputModel
            get() = GetUserOutputModel(
                username = "username",
                email = "email",
                points = 0,
                numberOfGamesPlayed = 0
            )
    }
}
