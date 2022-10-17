package pt.isel.daw.battleships.services.users

import pt.isel.daw.battleships.utils.ServerConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PasswordUtilsTests {

    @Test
    fun `PasswordUtils creation is successful`() {
        val secret = "secret"
        val serverConfiguration = ServerConfiguration(secret)
        PasswordUtils(serverConfig = serverConfiguration)
    }

    @Test
    fun `PasswordUtils hashPassword is successful`() {
        val secret = "secret"
        val serverConfiguration = ServerConfiguration(secret)
        val passwordUtils = PasswordUtils(serverConfig = serverConfiguration)

        val username = "bob"
        val password = "password"
        val hashedPassword = passwordUtils.hashPassword(username, password)

        assertEquals(64, hashedPassword.length)
    }

    @Test
    fun `PasswordUtils checkPassword is successful`() {
        val secret = "secret"
        val serverConfiguration = ServerConfiguration(secret)
        val passwordUtils = PasswordUtils(serverConfig = serverConfiguration)

        val username = "bob"
        val password = "password"
        val hashedPassword = passwordUtils.hashPassword(username, password)

        assertTrue(passwordUtils.checkPassword(username, password, hashedPassword))
    }
}
