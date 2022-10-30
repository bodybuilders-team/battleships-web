package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDTOTests {

    @Test
    fun `UserDTO creation is successful`() {
        UserDTO(
            username = "Player 1",
            email = "haram@email.com",
            points = 0,
            numberOfGamesPlayed = 0
        )
    }

    @Test
    fun `UserDTO from User conversion is successful`() {
        val user = defaultUser(0)

        val userDTO = UserDTO(user)

        assertEquals(user.username, userDTO.username)
        assertEquals(user.email, userDTO.email)
        assertEquals(user.points, userDTO.points)
    }

    companion object {
        fun defaultUserDTO(number: Int) = UserDTO(
            username = "User$number",
            email = "user$number@email.com",
            points = 0,
            numberOfGamesPlayed = 0
        )
    }
}
