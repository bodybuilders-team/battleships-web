package pt.isel.daw.battleships.services.users.dtos

import kotlin.test.Test

class CreateUserRequestDTOTests {
    @Test
    fun `CreateUserRequestDTO creation is successful`() {
        CreateUserRequestDTO("username", "email", "password")
    }
}