package pt.isel.daw.battleships

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ServerConfiguration(
    @Value("\${server.secret}")
    val serverSecret: String
)
