package pt.isel.daw.battleships.utils

import org.springframework.stereotype.Component
import java.security.MessageDigest

/**
 * Utility class.
 *
 * @property serverConfig the server configuration
 */
@Component
class Utils(private val serverConfig: ServerConfiguration) {

    /**
     * Generates a hash for the given [password].
     *
     * @param password the password to be hashed
     * @return the hash of the password
     */
    fun hashPassword(password: String): String =
        (password + serverConfig.serverSecret).sha256()

    /**
     * Generates a hash for the given [password] and compares it to the given [hashedPassword].
     *
     * @param password the password to be hashed
     * @param hashedPassword the hashed password to be compared to
     *
     * @return true if the passwords match, false otherwise
     */
    fun checkPassword(password: String, hashedPassword: String): Boolean =
        hashPassword(password) == hashedPassword

    /**
     * Generates a hash for the string using the SHA-256 algorithm.
     *
     * @return the hash of the string
     */
    fun String.sha256(): String = MessageDigest
        .getInstance("SHA256")
        .digest(this.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}
