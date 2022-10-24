package pt.isel.daw.battleships.domain

import kotlin.test.Test

class UserTests {

    @Test
    fun `User creation is successful`() {
        User("Player 1", "haram@email.com", "1234", 0)
    }

    companion object {
        val defaultUser
            get() = User("Player 1", "haram@email.com", "1234", 0)
    }
}