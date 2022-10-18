package pt.isel.daw.battleships.services.users.dtos

import pt.isel.daw.battleships.dtos.users.CreateUserRequestDTO
import kotlin.test.Test

class CreateUserRequestDTOTests {

    @Test
    fun `CreateUserRequestDTO creation is successful`() {
        CreateUserRequestDTO(username = "username", email = "email", password = "password")
    }
}
