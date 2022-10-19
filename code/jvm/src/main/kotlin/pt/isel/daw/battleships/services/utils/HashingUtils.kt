package pt.isel.daw.battleships.services.utils

import org.springframework.stereotype.Component
import pt.isel.daw.battleships.utils.ServerConfiguration
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Utility class.
 *
 * @property serverConfig the server configuration
 */
@Component
class HashingUtils(private val serverConfig: ServerConfiguration) {

    val tokenKey: SecretKey = SecretKeySpec(
        /* key = */ serverConfig.tokenHashSecret.toByteArray(),
        /* algorithm = */ "HmacSHA512"
    )
    val passwordKey: SecretKey = SecretKeySpec(
        /* key = */ serverConfig.passwordSecret.toByteArray(),
        /* algorithm = */ "HmacSHA512"
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
     * Generates a MAC for the given [
     */
    fun String.hmac(key: SecretKey): String {
        val mac = Mac.getInstance("HmacSHA512")
        mac.init(key)

        return mac.doFinal(this.toByteArray()).fold("") { str, it -> str + "%02x".format(it) }
    }
}
