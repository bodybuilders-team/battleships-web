package pt.isel.daw.battleships.domain

import pt.isel.daw.battleships.domain.UserTests.Companion.defaultUser
import java.time.Instant
import kotlin.test.Test

class RefreshTokenTests {

    @Test
    fun `RefreshToken creation is successful`() {
        RefreshToken(
            user = defaultUser,
            tokenHash = "token",
            expirationDate = Instant.now()
        )
    }

    companion object {
        val defaultRefreshToken
            get() = RefreshToken(
                user = defaultUser,
                tokenHash = "token",
                expirationDate = Instant.now()
            )
    }
}
