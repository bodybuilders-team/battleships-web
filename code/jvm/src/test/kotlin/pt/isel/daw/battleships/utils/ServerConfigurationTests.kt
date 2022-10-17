package pt.isel.daw.battleships.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class ServerConfigurationTests {

    @Test
    fun `ServerConfiguration creation is successful`() {
        val secret = "secret"
        val serverConfiguration = ServerConfiguration(serverSecret = secret)
        assertEquals(secret, serverConfiguration.serverSecret)
    }
}
