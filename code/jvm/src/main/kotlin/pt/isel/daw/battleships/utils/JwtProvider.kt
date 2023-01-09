package pt.isel.daw.battleships.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
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
    data class JwtPayload(val claims: Claims) {

        val username: String = claims[USERNAME_KEY] as String

        companion object {

            fun fromData(username: String): JwtPayload {
                val claims = Jwts.claims()
                claims[USERNAME_KEY] = username
                return JwtPayload(claims)
            }

            private const val USERNAME_KEY = "username"
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
            .setClaims(jwtPayload.claims)
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
                .setClaims(jwtPayload.claims)
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
            getAccessTokenPayload(token)
        } catch (e: ExpiredJwtException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: SignatureException) {
            null
        } catch (e: MalformedJwtException) {
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
            getRefreshTokenPayload(token)
        } catch (e: JwtException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: SignatureException) {
            null
        } catch (e: MalformedJwtException) {
            null
        }

    /**
     * Gets the payload of a JWT access token.
     *
     * @param token the token to get the payload from
     *
     * @return the payload of the token or null if an exception is thrown
     */
    fun getAccessTokenPayloadOrNull(token: String): JwtPayload? =
        try {
            getAccessTokenPayload(token)
        } catch (e: ExpiredJwtException) {
            e.claims?.let { JwtPayload(it) }
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: SignatureException) {
            null
        } catch (e: MalformedJwtException) {
            null
        }

    /**
     * Gets the payload of a JWT refresh token.
     *
     * @param token the token to get the payload from
     *
     * @return the payload of the token or null if an exception is thrown
     */
    fun getRefreshTokenPayloadOrNull(token: String): JwtPayload? =
        try {
            getRefreshTokenPayload(token)
        } catch (e: ExpiredJwtException) {
            e.claims?.let { JwtPayload(it) }
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: SignatureException) {
            null
        } catch (e: MalformedJwtException) {
            null
        }

    /**
     * Gets the payload of a JWT access token.
     *
     * @param token the token to get the payload from
     *
     * @return the payload of the token
     */
    fun getAccessTokenPayload(token: String) =
        getTokenClaims(token, accessTokenKey)

    /**
     * Gets the payload of a JWT refresh token.
     *
     * @param token the token to get the payload from
     *
     * @return the payload of the token
     */
    fun getRefreshTokenPayload(token: String) =
        getTokenClaims(token, refreshTokenKey)

    /**
     * Gets the payload of a JWT token.
     *
     * @param token the token to get the payload from
     *
     * @return the claims of the token
     */
    private fun getTokenClaims(token: String, tokenKey: Key) =
        JwtPayload(
            claims = Jwts.parserBuilder()
                .setSigningKey(tokenKey)
                .build()
                .parseClaimsJws(token).body
        )

    /**
     * Parses the bearer token.
     *
     * @param token the token to parse
     * @return the parsed bearer token or null if the token is not a bearer token
     */
    fun parseBearerToken(token: String): String? =
        if (!token.startsWith(prefix = BEARER_TOKEN_PREFIX))
            null
        else
            token.substringAfter(delimiter = BEARER_TOKEN_PREFIX)

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer "
        private const val SECRET_KEY_ALGORITHM = "HmacSHA512"
        const val ACCESS_TOKEN_ATTRIBUTE = "access_token"
        const val REFRESH_TOKEN_ATTRIBUTE = "refresh_token"

        val accessTokenDuration: Duration = Duration.ofHours(1)
        val refreshTokenDuration: Duration = Duration.ofDays(1)
    }
}
