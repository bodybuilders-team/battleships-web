package pt.isel.daw.battleships.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * Configuration of the server.
 *
 * @property accessTokenSecret the server secret
 */
@Configuration
class ServerConfiguration(
    @Value("\${server.config.secrets.accessTokenSecret}")
    val accessTokenSecret: String,
    @Value("\${server.config.secrets.refreshTokenSecret}")
    val refreshTokenSecret: String,
    @Value("\${server.config.secrets.passwordSecret}")
    val passwordSecret: String,
    @Value("\${server.config.secrets.tokenHashSecret}")
    val tokenHashSecret: String,
    @Value("\${server.config.maxRefreshTokens}")
    val maxRefreshTokens: Int
)
