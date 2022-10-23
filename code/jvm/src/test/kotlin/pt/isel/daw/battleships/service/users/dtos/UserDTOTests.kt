package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.domain.User
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDTOTests {

    @Test
    fun `UserDTO creation is successful`() {
        UserDTO(
            username = "username",
            email = "email",
            points = 0,
            numberOfGamesPlayed = 0
        )
    }

    @Test
    fun `UserDTO from User conversion is successful`() {
        val user = User(
            username = "username",
            email = "email",
            passwordHash = "password",
            points = 0
        )
        val userDTO = UserDTO(user)

        assertEquals(user.username, userDTO.username)
        assertEquals(user.email, userDTO.email)
        assertEquals(user.points, userDTO.points)
    }
}
