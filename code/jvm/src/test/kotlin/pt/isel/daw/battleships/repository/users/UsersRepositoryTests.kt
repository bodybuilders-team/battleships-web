package pt.isel.daw.battleships.repository.users

import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.testUtils.DatabaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UsersRepositoryTests(
    @Autowired
    val usersRepository: UsersRepository
) : DatabaseTest() {

    @Test
    fun `findByUsername returns the user with the given username`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        val foundUser = usersRepository.findByUsername("bob")

        assertNotNull(foundUser)
        assertEquals(user, foundUser)
    }

    @Test
    fun `findByUsername returns null if a user with the given username is not found`() {
        val foundUser = usersRepository.findByUsername("bob")

        assertNull(foundUser)
    }

    @Test
    fun `existsByUsername returns true if a user with the given username exists`() {
        entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        assertTrue(usersRepository.existsByUsername("bob"))
    }

    @Test
    fun `existsByUsername returns false if a user with the given username does not exist`() {
        assertFalse(usersRepository.existsByUsername("bob"))
    }

    @Test
    fun `existsByEmail returns true if a user with the given email exists`() {
        entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        assertTrue(usersRepository.existsByEmail("bob@bob.com"))
    }

    @Test
    fun `existsByEmail returns false if a user with the given email does not exist`() {
        assertFalse(usersRepository.existsByEmail("bob@bob.com"))
    }
}
