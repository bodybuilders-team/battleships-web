package pt.isel.daw.battleships.database.user

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pt.isel.daw.battleships.database.DatabaseTest
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.repositories.UsersRepository

class UserTests(
    @Autowired
    val usersRepository: UsersRepository
) : DatabaseTest() {

    @Test
    fun `Test exists by email`() {
        this.usersRepository.save(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        assertTrue(this.usersRepository.existsByEmail("bob@bob.com"))
    }

    @Test
    fun `Test exists by username`() {
        this.usersRepository.save(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )
        assertTrue(this.usersRepository.existsByUsername("bob"))
    }
}
