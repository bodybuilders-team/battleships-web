package pt.isel.daw.battleships.domain

import kotlin.test.Test

class UserTests {

    @Test
    fun `User creation is successful`() {
        User(username = "Player 1", email = "haram@email.com", passwordHash = "12345678", points = 0)
    }

    companion object {
        val defaultUser
            get() = User(username = "Player 1", email = "haram@email.com", passwordHash = "12345678", points = 0)
    }
}
