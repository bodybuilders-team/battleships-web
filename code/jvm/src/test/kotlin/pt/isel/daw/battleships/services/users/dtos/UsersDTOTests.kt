package pt.isel.daw.battleships.services.users.dtos

import pt.isel.daw.battleships.dtos.users.UserDTO
import pt.isel.daw.battleships.dtos.users.UsersDTO
import kotlin.test.Test

class UsersDTOTests {

    @Test
    fun `UsersDTO creation is successful`() {
        UsersDTO(
            users = listOf(
                UserDTO(
                    username = "Player 1",
                    email = "abc@mail.org",
                    points = 1
                )
            ),
            totalCount = 1
        )
    }
}
