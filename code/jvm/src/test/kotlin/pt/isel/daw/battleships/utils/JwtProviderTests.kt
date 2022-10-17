package pt.isel.daw.battleships.utils

import kotlin.test.Test

class JwtProviderTests {

    @Test
    fun `JwtProvider creation is successful`() {
        val secret = "secret"
        val serverConfiguration = ServerConfiguration(secret)
        JwtProvider(serverConfig = serverConfiguration)
    }

    // TODO: Add more tests
}
