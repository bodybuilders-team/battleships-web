package pt.isel.daw.battleships

import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class Utils(private val serverConfig: ServerConfiguration) {

    fun hashPassword(password: String): String {
        return (password + serverConfig.serverSecret).sha256()
    }

    fun checkPassword(password: String, hashedPassword: String): Boolean {
        return hashPassword(password) == hashedPassword
    }

    fun String.sha256(): String {
        return MessageDigest
            .getInstance("SHA256")
            .digest(this.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }
}
