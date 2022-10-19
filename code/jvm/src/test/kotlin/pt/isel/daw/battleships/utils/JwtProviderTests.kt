package pt.isel.daw.battleships.utils

import kotlin.test.Test

class JwtProviderTests {

    @Test
    fun `JwtProvider creation is successful`() {
        val serverConfiguration = ServerConfiguration(
            accessTokenSecret = "secret",
            refreshTokenSecret = "secret",
            passwordSecret = "secret",
            tokenHashSecret = "secret",
            maxRefreshTokens = 10
        )
        JwtProvider(serverConfig = serverConfiguration)
    }

    // TODO: Add more tests
}
