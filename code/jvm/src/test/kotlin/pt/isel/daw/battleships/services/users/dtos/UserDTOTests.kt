package pt.isel.daw.battleships.services.users.dtos

import pt.isel.daw.battleships.database.model.User
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDTOTests {
    @Test
    fun `UserDTO creation is successful`() {
        UserDTO("username", "email", 0)
    }

    @Test
    fun `UserDTO from User conversion is successful`() {
        val user = User("username", "email", "password", 0)
        val userDTO = UserDTO(user)

        assertEquals(user.username, userDTO.username)
        assertEquals(user.email, userDTO.email)
        assertEquals(user.points, userDTO.points)
    }
}