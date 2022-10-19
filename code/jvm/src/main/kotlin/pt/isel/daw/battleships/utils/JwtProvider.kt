package pt.isel.daw.battleships.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.Date
import javax.crypto.spec.SecretKeySpec

/**
 * Utility class for JWT operations.
 *
 * @param serverConfig the server configuration
 * @property accessTokenKey the key used to sign the JWT
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
     * Represents a JWT Payload.
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
     * Creates a JWT token for the given payload.
     *
     * @param jwtPayload the payload to be signed
     * @return the JWT token
     */
    fun createToken(jwtPayload: JwtPayload): String {
        val issuedAt = Instant.now()
        val expirationDate = issuedAt.plus(ACCESS_TOKEN_DURATION)

        return Jwts.builder()
            .setClaims(jwtPayload.toClaims())
            .signWith(accessTokenKey)
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(expirationDate))
            .compact()
    }

    fun createRefreshToken(jwtPayload: JwtPayload): RefreshTokenDetails {
        val issuedAt = Instant.now()
        val expirationDate = issuedAt.plus(REFRESH_TOKEN_DURATION)

        return RefreshTokenDetails(
            token = Jwts.builder()
                .setClaims(jwtPayload.toClaims())
                .signWith(refreshTokenKey)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expirationDate))
                .compact(),
            expirationDate = expirationDate
        )
    }

    /**
     * Validates a JWT token and returns the payload.
     *
     * @param token the token to validate
     * @return the payload of the token or null if an exception is thrown
     */
    fun validateToken(token: String): JwtPayload? = try {
        val claims = Jwts.parserBuilder()
            .setSigningKey(accessTokenKey)
            .build()
            .parseClaimsJws(token).body

        JwtPayload.fromClaims(claims)
    } catch (e: JwtException) {
        null
    } catch (e: IllegalArgumentException) {
        null
    } catch (e: SignatureException) {
        null
    }

    fun validateRefreshToken(token: String): JwtPayload? = try {
        val claims = Jwts.parserBuilder()
            .setSigningKey(refreshTokenKey)
            .build()
            .parseClaimsJws(token).body

        JwtPayload.fromClaims(claims)
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
        val ACCESS_TOKEN_DURATION: TemporalAmount = Duration.ofHours(1)
        val REFRESH_TOKEN_DURATION: TemporalAmount = Duration.ofDays(1)
    }
}

data class RefreshTokenDetails(val token: String, val expirationDate: Instant)
