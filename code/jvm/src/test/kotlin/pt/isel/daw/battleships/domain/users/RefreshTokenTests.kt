package pt.isel.daw.battleships.domain.users

import pt.isel.daw.battleships.domain.exceptions.InvalidRefreshTokenException
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import java.sql.Timestamp
import java.time.Instant
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class RefreshTokenTests {

    @Test
    fun `RefreshToken creation is successful`() {
        val user = defaultUser(0)
        val tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH)
        val expirationDate = Timestamp.from(Instant.now())

        val refreshToken = RefreshToken(
            user = user,
            tokenHash = tokenHash,
            expirationDate = expirationDate
        )

        val refreshTokenId = RefreshToken::class.declaredMemberProperties
            .first { it.name == "id" }.also { it.isAccessible = true }
            .call(refreshToken) as Int?

        assertNull(refreshTokenId)
        assertEquals(user, refreshToken.user)
        assertEquals(tokenHash, refreshToken.tokenHash)
        assertEquals(expirationDate, refreshToken.expirationDate)
    }

    @Test
    fun `RefreshToken creation throws InvalidRefreshTokenException if the token hash length is invalid`() {
        assertFailsWith<InvalidRefreshTokenException> {
            RefreshToken(
                user = defaultUser(0),
                tokenHash = "invalidTokenHash",
                expirationDate = Timestamp.from(Instant.now())
            )
        }
    }

    companion object {
        val defaultRefreshToken
            get() = RefreshToken(
                user = defaultUser(0),
                tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH),
                expirationDate = Timestamp.from(Instant.now())
            )
    }
}
