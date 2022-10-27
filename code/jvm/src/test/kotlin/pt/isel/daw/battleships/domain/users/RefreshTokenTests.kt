package pt.isel.daw.battleships.domain.users

import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test

class RefreshTokenTests {

    @Test
    fun `RefreshToken creation is successful`() {
        RefreshToken(
            user = defaultUser,
            tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH),
            expirationDate = Timestamp.from(Instant.now())
        )
    }

    companion object {
        val defaultRefreshToken
            get() = RefreshToken(
                user = defaultUser,
                tokenHash = "a".repeat(RefreshToken.TOKEN_HASH_LENGTH),
                expirationDate = Timestamp.from(Instant.now())
            )
    }
}
