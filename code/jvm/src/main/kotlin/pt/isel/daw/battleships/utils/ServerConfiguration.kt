package pt.isel.daw.battleships.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * Configuration of the server.
 *
 * @property serverSecret the server secret
 */
@Configuration
class ServerConfiguration(
    @Value("\${server.secret}")
    val serverSecret: String
)
