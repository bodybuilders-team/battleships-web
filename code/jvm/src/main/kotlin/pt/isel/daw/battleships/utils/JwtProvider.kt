package pt.isel.daw.battleships.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.Date
import javax.crypto.spec.SecretKeySpec

/**
 * Utility class for JWT operations.
 *
 * @param serverConfig the server configuration
 * @property accessTokenKey the key used to sign access tokens
 * @property refreshTokenKey the key used to sign refresh tokens
 */
@Component
class JwtProvider(serverConfig: ServerConfiguration) {

    private val accessTokenKey = SecretKeySpec(
        /* key = */ serverConfig.accessTokenSecret.toByteArray(),
        /* algorithm = */ SECRET_KEY_ALGORITHM
    )

    private val refreshTokenKey = SecretKeySpec(
        /* key = */ serverConfig.refreshTokenSecret.toByteArray(),
        /* algorithm = */ SECRET_KEY_ALGORITHM
    )

    /**
     * A JWT Payload.
     *
     * @property username the username of the user
     */
    data class JwtPayload(val username: String) {

        /**
         * Converts the JWT payload to a [Claims] object.
         *
         * @return the [Claims] object
         */
        fun toClaims(): Claims = Jwts.claims(
            /* claims = */ mapOf(USERNAME_KEY to username)
        )

        companion object {
            private const val USERNAME_KEY = "username"

            /**
             * Creates a [JwtPayload] from the given [claims].
             *
             * @param claims the claims to create the payload from
             * @return the created payload
             */
            fun fromClaims(claims: Claims) = JwtPayload(
                username = claims[USERNAME_KEY] as String
            )
        }
    }

    /**
     * A Refresh Token.
     *
     * @property token the token
     * @property expirationDate the expiration date of the token
     */
    data class RefreshTokenDetails(
        val token: String,
        val expirationDate: Timestamp
    )

    /**
     * Creates a JWT access token.
     *
     * @param jwtPayload the payload to be signed
     * @return the JWT token
     */
    fun createAccessToken(jwtPayload: JwtPayload): String {
        val issuedAt = Instant.now()
        val expirationDate = issuedAt.plus(accessTokenDuration)

        return Jwts.builder()
            .setClaims(jwtPayload.toClaims())
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(expirationDate))
            .signWith(accessTokenKey)
            .compact()
    }

    /**
     * Creates a JWT refresh token.
     *
     * @param jwtPayload the payload to be signed
     * @return the JWT token
     */
    fun createRefreshToken(jwtPayload: JwtPayload): RefreshTokenDetails {
        val issuedAt = Instant.now()
        val expirationDate = issuedAt.plus(refreshTokenDuration)

        return RefreshTokenDetails(
            token = Jwts.builder()
                .setClaims(jwtPayload.toClaims())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expirationDate))
                .signWith(refreshTokenKey)
                .compact(),
            expirationDate = Timestamp.from(expirationDate)
        )
    }

    /**
     * Validates a JWT access token and returns the payload.
     *
     * @param token the token to validate
     * @return the payload of the token or null if an exception is thrown
     */
    fun validateAccessToken(token: String): JwtPayload? =
        try {
            JwtPayload.fromClaims(
                claims = Jwts.parserBuilder()
                    .setSigningKey(accessTokenKey)
                    .build()
                    .parseClaimsJws(token).body
            )
        } catch (e: JwtException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: SignatureException) {
            null
        }

    /**
     * Validates a JWT refresh token and returns the payload.
     *
     * @param token the token to validate
     * @return the payload of the token or null if an exception is thrown
     */
    fun validateRefreshToken(token: String): JwtPayload? =
        try {
            JwtPayload.fromClaims(
                claims = Jwts.parserBuilder()
                    .setSigningKey(refreshTokenKey)
                    .build()
                    .parseClaimsJws(token).body
            )
        } catch (e: JwtException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: SignatureException) {
            null
        }

    /**
     * Parses the bearer token.
     *
     * @param token the token to parse
     * @return the parsed bearer token or null if the token is not a bearer token
     */
    fun parseBearerToken(token: String): String? =
        if (!token.startsWith(prefix = BEARER_TOKEN_PREFIX)) {
            null
        } else {
            token.substringAfter(delimiter = BEARER_TOKEN_PREFIX)
        }

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer "
        private const val SECRET_KEY_ALGORITHM = "HmacSHA512"
        const val TOKEN_ATTRIBUTE = "token"

        val accessTokenDuration: TemporalAmount = Duration.ofHours(1)
        val refreshTokenDuration: TemporalAmount = Duration.ofDays(1)
    }
}
