package pt.isel.daw.battleships.service.utils

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest
class HashingUtilsTests {

    @Autowired
    lateinit var hashingUtils: HashingUtils

    private val hashingUtilsPasswordKey by lazy {
        hashingUtils::class.declaredMemberProperties
            .first { it.name == "passwordKey" }.also { it.isAccessible = true }
            .call(hashingUtils) as SecretKey
    }

    private val hashingUtilsTokenKey by lazy {
        hashingUtils::class.declaredMemberProperties
            .first { it.name == "tokenKey" }.also { it.isAccessible = true }
            .call(hashingUtils) as SecretKey
    }

    private val hashingUtilsSecretKeyAlgorithm by lazy {
        HashingUtils.Companion::class.declaredMemberProperties
            .first { it.name == "SECRET_KEY_ALGORITHM" }.also { it.isAccessible = true }
            .call(hashingUtils) as String
    }

    private val hashingUtilsHmacPrivateKFunction by lazy {
        hashingUtils::class.declaredFunctions
            .first { it.name == "hmac" }.also { it.isAccessible = true }
    }

    /**
     * Hmac function created for the purpose of testing the private hmac function by comparison of the value returned.
     *
     * @param string the string to be hashed
     * @param key the key to be used in the hashing
     */
    private fun testHmac(string: String, key: SecretKey) =
        Mac.getInstance(hashingUtilsSecretKeyAlgorithm)
            .also { it.init(key) }
            .doFinal((string).toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }

    @Test
    fun `hashPassword returns the generated hash of the password`() {
        val username = "bob"
        val password = "password"
        val hmac = testHmac(username + password, hashingUtilsPasswordKey)

        val passwordHash = hashingUtils.hashPassword(username, password)

        assertEquals(128, passwordHash.length)
        assertEquals(hmac, passwordHash)
    }

    @Test
    fun `hashToken returns the generated hash of the token`() {
        val token = "tokenHash"
        val hmac = testHmac(token, hashingUtilsTokenKey)

        val tokenHash = hashingUtils.hashToken(token)

        assertEquals(128, tokenHash.length)
        assertEquals(hmac, tokenHash)
    }

    @Test
    fun `checkPassword returns true if password hash matches password`() {
        val username = "bob"
        val password = "password"
        val passwordHash = hashingUtils.hashPassword(username, password)

        val passwordsMatch = hashingUtils.checkPassword(username, password, passwordHash)

        assertTrue(passwordsMatch)
    }

    @Test
    fun `checkPassword returns false if password hash does not match password`() {
        val username = "bob"
        val password = "password"
        val passwordHash = hashingUtils.hashPassword(username, "anotherPassword")

        val passwordsMatch = hashingUtils.checkPassword(username, password, passwordHash)

        assertFalse(passwordsMatch)
    }

    @Test
    fun `hmac returns MAC for the given string using the key`() {
        val string = "string"
        val key = SecretKeySpec(
            /* key = */ "secret".toByteArray(),
            /* algorithm = */ hashingUtilsSecretKeyAlgorithm
        )
        val hmac = testHmac(string, key)

        val hmacResult = hashingUtilsHmacPrivateKFunction.call(hashingUtils, string, key) as String

        assertEquals(128, hmacResult.length)
        assertEquals(hmac, hmacResult)
    }
}
