package pt.isel.daw.battleships.utils

import io.jsonwebtoken.Jwts
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.sql.Timestamp
import java.time.Instant
import javax.crypto.SecretKey
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(SpringRunner::class)
@SpringBootTest
class JwtProviderTests {

    @Autowired
    lateinit var jwtProvider: JwtProvider

    val jwtProviderAccessTokenKey by lazy {
        jwtProvider::class.declaredMemberProperties
            .first { it.name == "accessTokenKey" }.also { it.isAccessible = true }
            .call(jwtProvider) as SecretKey
    }

    val jwtProviderRefreshTokenKey by lazy {
        jwtProvider::class.declaredMemberProperties
            .first { it.name == "refreshTokenKey" }.also { it.isAccessible = true }
            .call(jwtProvider) as SecretKey
    }

    /**
     * Function created for the purpose of getting the claims given a [token] and a [key].
     *
     * @param token the token to be parsed
     * @param key the key to be used in the parsing
     */
    private fun getClaimsFromToken(token: String, key: SecretKey) =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

    @Test
    fun `JwtProvider creation is successful`() {
        JwtProvider(
            serverConfig = ServerConfiguration(
                accessTokenSecret = "accessTokenSecret",
                refreshTokenSecret = "refreshTokenSecret",
                passwordSecret = "passwordSecret",
                tokenHashSecret = "tokenHashSecret",
                maxRefreshTokens = 10
            )
        )
    }

    @Test
    fun `createAccessToken returns a JWT access token with the correct payload`() {
        val payload = JwtProvider.JwtPayload.fromData(username = "bob")

        val token = jwtProvider.createAccessToken(payload)

        val claims = getClaimsFromToken(token, jwtProviderAccessTokenKey)

        assertEquals(payload.username, claims["username"])
    }

    @Test
    fun `createRefreshToken returns a JWT refresh token`() {
        val payload = JwtProvider.JwtPayload.fromData(username = "bob")

        val (token) = jwtProvider.createRefreshToken(payload)

        val claims = getClaimsFromToken(token, jwtProviderRefreshTokenKey)

        assertEquals(payload.username, claims["username"])
    }

    @Test
    fun `validateAccessToken returns the payload if the token is valid`() {
        val payload = JwtProvider.JwtPayload.fromData(username = "bob")

        val token = jwtProvider.createAccessToken(payload)

        val validatedPayload = jwtProvider.validateAccessToken(token)

        assertNotNull(validatedPayload)
        assertEquals(payload.username, validatedPayload.username)
    }

    @Test
    fun `validateAccessToken returns null if the token is not valid`() {
        val token = "invalidToken"

        val validatedPayload = jwtProvider.validateAccessToken(token)

        assertNull(validatedPayload)
    }

    @Test
    fun `validateRefreshToken returns the payload if the token is valid`() {
        val payload = JwtProvider.JwtPayload.fromData(username = "bob")

        val (token) = jwtProvider.createRefreshToken(payload)

        val validatedPayload = jwtProvider.validateRefreshToken(token)

        assertNotNull(validatedPayload)
        assertEquals(payload.username, validatedPayload.username)
    }

    @Test
    fun `validateRefreshToken returns null if the token is not valid`() {
        val token = "invalidToken"

        val validatedPayload = jwtProvider.validateRefreshToken(token)

        assertNull(validatedPayload)
    }

    @Test
    fun `parseBearerToken returns the parsed the bearer token`() {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImJvYiJ9.3Z5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQ"

        val parsedToken = jwtProvider.parseBearerToken(token)

        assertNotNull(parsedToken)
        assertEquals(
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImJvYiJ9.3Z5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQ",
            parsedToken
        )
    }

    @Test
    fun `parseBearerToken returns null if it is not a bearer token`() {
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImJvYiJ9.3Z5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQZ5ZQ"

        val parsedToken = jwtProvider.parseBearerToken(token)

        assertNull(parsedToken)
    }

    // JwtPayload

    @Test
    fun `JwtPayload creation is successful`() {
        JwtProvider.JwtPayload.fromData(username = "bob")
    }

    @Test
    fun `JwtPayload to Claims conversion is successful`() {
        val jwtPayload = JwtProvider.JwtPayload.fromData(username = "bob")

        val claims = jwtPayload.claims

        assertEquals(jwtPayload.username, claims["username"])
    }

    @Test
    fun `JwtPayload from Claims conversion is successful`() {
        val claims = Jwts.claims(mapOf("username" to "bob"))

        val jwtPayload = JwtProvider.JwtPayload(claims)

        assertEquals(claims["username"], jwtPayload.username)
    }

    // RefreshTokenDetails

    @Test
    fun `RefreshTokenDetails creation is successful`() {
        JwtProvider.RefreshTokenDetails(token = "token", expirationDate = Timestamp.from(Instant.now()))
    }
}
