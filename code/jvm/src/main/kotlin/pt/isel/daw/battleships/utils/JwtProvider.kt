package pt.isel.daw.battleships.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.SignatureException
import javax.crypto.spec.SecretKeySpec

/**
 * Utility class for JWT operations.
 *
 * @param config the server configuration
 * @property key the key used to sign the JWT
 */
@Component
class JwtProvider(config: ServerConfiguration) {

    private val key = SecretKeySpec(config.serverSecret.toByteArray(), SECRET_KEY_ALGORITHM)

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
            mapOf(USERNAME_KEY to username)
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
    fun createToken(jwtPayload: JwtPayload): String = Jwts.builder()
        .setClaims(jwtPayload.toClaims())
        .signWith(key)
        .setIssuedAt(java.util.Date())
        .compact()

    /**
     * Validates a JWT token and returns the payload.
     *
     * @param token the token to validate
     * @return the payload of the token or null if an exception is thrown
     */
    fun validateToken(token: String): JwtPayload? = try {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
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
        if (!token.startsWith(BEARER_TOKEN_PREFIX)) {
            null
        } else {
            token.substringAfter(BEARER_TOKEN_PREFIX)
        }

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer "
        private const val SECRET_KEY_ALGORITHM = "HmacSHA256"
        const val TOKEN_ATTRIBUTE = "token"
    }
}
