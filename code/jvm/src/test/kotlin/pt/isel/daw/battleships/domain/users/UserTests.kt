package pt.isel.daw.battleships.domain.users

import pt.isel.daw.battleships.domain.exceptions.InvalidUserException
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class UserTests {

    @Test
    fun `User creation is successful`() {
        val passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)

        val user = User(
            username = "User1",
            email = "user1@email.com",
            passwordHash = passwordHash
        )

        val userId = User::class.declaredMemberProperties
            .first { it.name == "id" }.also { it.isAccessible = true }
            .call(user) as Int?

        assertNull(userId)
        assertEquals("User1", user.username)
        assertEquals("user1@email.com", user.email)
        assertEquals(passwordHash, user.passwordHash)
        assertEquals(0, user.points)
        assertEquals(0, user.numberOfGamesPlayed)
    }

    @Test
    fun `User creation throws InvalidUserException if username is shorter than minimum username length`() {
        assertFailsWith<InvalidUserException> {
            User(
                username = "a".repeat(2),
                email = "user1@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        }
    }

    @Test
    fun `User creation throws InvalidUserException if username is longer than maximum username length`() {
        assertFailsWith<InvalidUserException> {
            User(
                username = "a".repeat(41),
                email = "user1@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        }
    }

    @Test
    fun `User creation throws InvalidUserException if email is not a valid email address`() {
        assertFailsWith<InvalidUserException> {
            User(
                username = "User1",
                email = "invalidMail",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        }
    }

    @Test
    fun `User creation throws InvalidUserException if password hash length is invalid`() {
        assertFailsWith<InvalidUserException> {
            User(
                username = "User1",
                email = "user1@email.com",
                passwordHash = "a".repeat(20)
            )
        }
    }

    @Test
    fun `User creation throws InvalidUserException if points is negative`() {
        assertFailsWith<InvalidUserException> {
            User(
                username = "User1",
                email = "user1@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = -1
            )
        }
    }

    @Test
    fun `User creation throws InvalidUserException if number of played games is negative`() {
        assertFailsWith<InvalidUserException> {
            User(
                username = "User1",
                email = "user1@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0,
                numberOfGamesPlayed = -1
            )
        }
    }

    companion object {
        fun defaultUser(number: Int) = User(
            username = "User$number",
            email = "user$number@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
        )
    }
}
