package pt.isel.daw.battleships.service

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.battleships.domain.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.repository.users.users.UsersRepository
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.utils.JwtProvider
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(SpringRunner::class)
@SpringBootTest
class AuthenticatedServiceTests {

    @MockBean
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    val authenticatedService by lazy {
        object : AuthenticatedService(
            usersRepository,
            jwtProvider
        ) {}
    }

    @Test
    fun `authenticateUser returns the authenticated user`() {
        val user = defaultUser

        Mockito.`when`(usersRepository.findByUsername(defaultUser.username))
            .thenReturn(user)

        val authenticatedUser = authenticatedService.authenticateUser(
            token = jwtProvider.createAccessToken(
                jwtPayload = JwtProvider.JwtPayload(username = defaultUser.username)
            )
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
                    jwtPayload = JwtProvider.JwtPayload(username = defaultUser.username)
                )
            )
        }
    }
}