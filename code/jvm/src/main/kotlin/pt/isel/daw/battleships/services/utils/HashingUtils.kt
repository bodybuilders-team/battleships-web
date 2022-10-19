package pt.isel.daw.battleships.services.utils

import org.springframework.stereotype.Component
import pt.isel.daw.battleships.utils.ServerConfiguration
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Utility class for hashing operations.
 *
 * @property serverConfig the server configuration
 * @property tokenKey the token key
 * @property passwordKey the password key
 */
@Component
class HashingUtils(private val serverConfig: ServerConfiguration) {

    val tokenKey: SecretKey = SecretKeySpec(
        /* key = */ serverConfig.tokenHashSecret.toByteArray(),
        /* algorithm = */ SECRET_KEY_ALGORITHM
    )

    val passwordKey: SecretKey = SecretKeySpec(
        /* key = */ serverConfig.passwordSecret.toByteArray(),
        /* algorithm = */ SECRET_KEY_ALGORITHM
    )

    /**
     * Generates a hash for the given [password].
     *
     * @param username the username to be hashed
     * @param password the password to be hashed
     *
     * @return the hash of the password
     */
    fun hashPassword(username: String, password: String): String =
        (username + password).hmac(passwordKey)

    /**
     * Generates a hash for the given [token].
     *
     * @param token the token to be hashed
     * @return the hash of the token
     */
    fun hashToken(token: String): String =
        (token).hmac(tokenKey)

    /**
     * Generates a hash for the given [password] and compares it to the given [hashedPassword].
     *
     * @param username the username to be hashed
     * @param password the password to be hashed
     * @param hashedPassword the hashed password to be compared to
     *
     * @return true if the passwords match, false otherwise
     */
    fun checkPassword(username: String, password: String, hashedPassword: String): Boolean =
        hashPassword(username, password) == hashedPassword

    /**
     * Generates a MAC for this given string using the given [key].
     *
     * @receiver the string to be hashed
     * @param key the key to be used in the hashing
     *
     * @return the hash of the string
     */
    private fun String.hmac(key: SecretKey): String =
        Mac.getInstance(SECRET_KEY_ALGORITHM)
            .also { it.init(key) }
            .doFinal(this.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }

    companion object {
        private const val SECRET_KEY_ALGORITHM = "HmacSHA512"
    }
}
