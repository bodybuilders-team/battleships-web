package pt.isel.daw.battleships

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.SignatureException
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(private val config: ServerConfiguration) {

    private val key = SecretKeySpec(config.serverSecret.toByteArray(), "HmacSHA256")

    data class JwtPayload(val username: String) {
        fun toClaims(): Claims = Jwts.claims(
            mapOf("username" to username)
        )

        companion object {
            fun fromClaims(claims: Claims) = JwtPayload(
                username = claims["username"] as String

            )
        }
    }

    fun createToken(jwtPayload: JwtPayload): String {
        return Jwts.builder()
            .setClaims(jwtPayload.toClaims())
            .signWith(key)
            .setIssuedAt(java.util.Date())
            .compact()
    }

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

    fun parseBearerToken(token: String): String? {
        if (!token.startsWith("Bearer ")) {
            return null
        }
        return token.substringAfter("Bearer ")
    }
}
