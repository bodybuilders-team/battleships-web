package pt.isel.daw.battleships.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * Configuration of the server.
 *
 * @property accessTokenSecret the secret used to sign the access tokens
 * @property refreshTokenSecret the secret used to sign the refresh tokens
 * @property passwordSecret the secret used to sign the passwords
 * @property tokenHashSecret the secret used to sign the token hashes
 * @property maxRefreshTokens the maximum number of refresh tokens that can be issued for a user
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
