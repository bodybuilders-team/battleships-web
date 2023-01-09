package pt.isel.daw.battleships.service

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.battleships.domain.users.RevokedAccessToken
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.repository.users.RevokedAccessTokensRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.utils.HashingUtils
import pt.isel.daw.battleships.utils.JwtProvider
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticatedServiceTests {

    @MockBean
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    @MockBean
    private lateinit var revokedAccessTokensRepository: RevokedAccessTokensRepository

    @Autowired
    private lateinit var hashingUtils: HashingUtils

    private val authenticatedService: AuthenticatedService by lazy {
        object :
            AuthenticatedService(
                usersRepository,
                revokedAccessTokensRepository,
                jwtProvider,
                hashingUtils
            ) {}
    }

    @Test
    fun `authenticateUser returns the authenticated user`() {
        val user = defaultUser(0)

        Mockito.`when`(usersRepository.findByUsername(defaultUser(0).username))
            .thenReturn(user)

        val token = jwtProvider.createAccessToken(
            jwtPayload = JwtProvider.JwtPayload.fromData(username = defaultUser(0).username)
        )

        Mockito.`when`(
            revokedAccessTokensRepository.findByUserAndTokenHash(
                user,
                tokenHash =
                hashingUtils.hashToken(token)
            )
        ).thenReturn(null)

        val authenticatedUser = authenticatedService.authenticateUser(
            token = token
        )

        assertEquals(user, authenticatedUser)
    }

    @Test
    fun `authenticateUser throws AuthenticationException if the token is invalid`() {
        assertFailsWith<AuthenticationException> {
            authenticatedService.authenticateUser(
                token = "invalidToken"
            )
        }
    }

    @Test
    fun `authenticateUser throws NotFoundException if the user does not exist`() {
        assertFailsWith<NotFoundException> {
            authenticatedService.authenticateUser(
                token = jwtProvider.createAccessToken(
                    jwtPayload = JwtProvider.JwtPayload.fromData(username = defaultUser(0).username)
                )
            )
        }
    }
}
