package pt.isel.daw.battleships.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

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
@EnableScheduling
class ServerConfiguration(
    @Value("\${server.config.secrets.access-token-secret}")
    val accessTokenSecret: String,

    @Value("\${server.config.secrets.refresh-token-secret}")
    val refreshTokenSecret: String,

    @Value("\${server.config.secrets.password-secret}")
    val passwordSecret: String,

    @Value("\${server.config.secrets.token-hash-secret}")
    val tokenHashSecret: String,

    @Value("\${server.config.max-refresh-tokens}")
    val maxRefreshTokens: Int
)
