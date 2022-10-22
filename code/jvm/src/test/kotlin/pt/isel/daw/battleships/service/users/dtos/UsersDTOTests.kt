package pt.isel.daw.battleships.service.users.dtos

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
