package pt.isel.daw.battleships.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class ServerConfigurationTests {

    @Test
    fun `ServerConfiguration creation is successful`() {
        val accessTokenSecret = "accessTokenSecret"
        val refreshTokenSecret = "refreshTokenSecret"
        val passwordSecret = "passwordSecret"
        val tokenHashSecret = "tokenHashSecret"
        val maxRefreshTokens = 10

        val serverConfiguration = ServerConfiguration(
            accessTokenSecret = accessTokenSecret,
            refreshTokenSecret = refreshTokenSecret,
            passwordSecret = passwordSecret,
            tokenHashSecret = tokenHashSecret,
            maxRefreshTokens = maxRefreshTokens
        )

        assertEquals(accessTokenSecret, serverConfiguration.accessTokenSecret)
        assertEquals(refreshTokenSecret, serverConfiguration.refreshTokenSecret)
        assertEquals(passwordSecret, serverConfiguration.passwordSecret)
        assertEquals(tokenHashSecret, serverConfiguration.tokenHashSecret)
        assertEquals(maxRefreshTokens, serverConfiguration.maxRefreshTokens)
    }
}
