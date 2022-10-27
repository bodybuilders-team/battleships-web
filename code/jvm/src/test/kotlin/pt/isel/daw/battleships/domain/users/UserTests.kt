package pt.isel.daw.battleships.domain.users

import kotlin.test.Test

class UserTests {

    @Test
    fun `User creation is successful`() {
        User(
            username = "Player 1",
            email = "haram@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
    }

    companion object {
        val defaultUser
            get() = User(
                username = "Player 1",
                email = "haram@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
    }
}
