package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
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
        val user = defaultUser

        val userDTO = UserDTO(user)

        assertEquals(user.username, userDTO.username)
        assertEquals(user.email, userDTO.email)
        assertEquals(user.points, userDTO.points)
    }

    companion object {
        val defaultUserDTO
            get() = UserDTO(
                username = "username",
                email = "email",
                points = 0,
                numberOfGamesPlayed = 0
            )
    }
}
