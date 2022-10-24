package pt.isel.daw.battleships.service.users.dtos

import pt.isel.daw.battleships.service.users.dtos.UserDTOTests.Companion.defaultUserDTO
import kotlin.test.Test

class UsersDTOTests {

    @Test
    fun `UsersDTO creation is successful`() {
        UsersDTO(
            users = listOf(defaultUserDTO),
            totalCount = 1
        )
    }

    companion object {
        val defaultUsersDTO
            get() = UsersDTO(
                users = listOf(defaultUserDTO),
                totalCount = 1
            )
    }
}
