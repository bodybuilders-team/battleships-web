package pt.isel.daw.battleships.repository.refreshTokens

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import pt.isel.daw.battleships.domain.users.RefreshToken
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.repository.users.RefreshTokensRepository
import pt.isel.daw.battleships.testUtils.DatabaseTest
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RefreshTokensRepositoryTests : DatabaseTest() {
    @Autowired
    lateinit var refreshTokensRepository: RefreshTokensRepository

    @Test
    fun `findByUserAndTokenHash returns the refresh token given the user and the token hash`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH)

        val refreshToken = entityManager.persist(
            RefreshToken(
                user = user,
                tokenHash = tokenHash,
                expirationDate = Timestamp.from(Instant.now())
            )
        )

        val token = refreshTokensRepository.findByUserAndTokenHash(user, tokenHash)

        assertNotNull(token)
        assertEquals(refreshToken, token)
    }

    @Test
    fun `findByUserAndTokenHash returns null if the user does not have a refresh token with the given token hash`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val token = refreshTokensRepository.findByUserAndTokenHash(user, "tokenHash")

        assertNull(token)
    }

    @Test
    fun `countByUser returns the number of refresh tokens for the given user`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        repeat(3) {
            entityManager.persist(
                RefreshToken(
                    user = user,
                    tokenHash = "$it".repeat(RefreshToken.TOKEN_HASH_LENGTH),
                    expirationDate = Timestamp.from(Instant.now())
                )
            )
        }

        val count = refreshTokensRepository.countByUser(user)

        assertEquals(3, count)
    }

    @Test
    fun `countByUser returns 0 if the given user has no refresh tokens`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val count = refreshTokensRepository.countByUser(user)

        assertEquals(0, count)
    }

    @Test
    fun `deleteByUserAndTokenHash deletes the refresh token given the user and the token hash`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH)

        entityManager.persist(
            RefreshToken(
                user = user,
                tokenHash = tokenHash,
                expirationDate = Timestamp.from(Instant.now())
            )
        )

        assertNotNull(refreshTokensRepository.findByUserAndTokenHash(user, tokenHash))

        refreshTokensRepository.deleteByUserAndTokenHash(user, tokenHash)

        assertNull(refreshTokensRepository.findByUserAndTokenHash(user, tokenHash))
    }

    @Test
    fun `existsByUserAndTokenHash returns true if the user has a refresh token with the given token hash`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH)

        entityManager.persist(
            RefreshToken(
                user = user,
                tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH),
                expirationDate = Timestamp.from(Instant.now())
            )
        )

        val exists = refreshTokensRepository.existsByUserAndTokenHash(user, tokenHash)

        assertTrue(exists)
    }

    @Test
    fun `existsByUserAndTokenHash returns false if the user does not have a refresh token with the given token hash`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val exists = refreshTokensRepository.existsByUserAndTokenHash(user, "tokenHash")

        assertFalse(exists)
    }

    @Test
    fun `getRefreshTokensOfUserOrderedByExpirationDate returns the refresh tokens of the given user ordered by expiration date`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val tokens = List(3) {
            entityManager.persist(
                RefreshToken(
                    user = user,
                    tokenHash = "$it".repeat(RefreshToken.TOKEN_HASH_LENGTH),
                    expirationDate = Timestamp.from(Instant.now())
                )
            )
        }

        val foundTokens = refreshTokensRepository.getRefreshTokensOfUserOrderedByExpirationDate(
            user = user,
            pageable = PageRequest.of(/* page = */ 0, /* size = */ 3)
        ).toList()

        assertEquals(3, foundTokens.size)
        assertEquals(tokens, foundTokens)
    }

    @Test
    fun `getRefreshTokensOfUserOrderedByExpirationDate returns empty if there are no refresh tokens of the given user`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val foundTokens = refreshTokensRepository.getRefreshTokensOfUserOrderedByExpirationDate(
            user = user,
            pageable = PageRequest.of(/* page = */ 0, /* size = */ 3)
        ).toList()

        assertEquals(0, foundTokens.size)
        assertEquals(emptyList(), foundTokens)
    }
}
