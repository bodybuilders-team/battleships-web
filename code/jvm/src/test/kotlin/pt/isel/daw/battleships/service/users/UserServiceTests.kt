package pt.isel.daw.battleships.service.users

import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.battleships.domain.users.RefreshToken
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.repository.users.RefreshTokensRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.users.dtos.UserDTO
import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserInputDTO
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTests {
    @MockBean
    lateinit var usersRepository: UsersRepository

    @MockBean
    lateinit var refreshTokensRepository: RefreshTokensRepository

    @Autowired
    lateinit var userService: UsersService

    @Test
    fun `Get user works`() {
        Mockito.`when`(usersRepository.findByUsername(defaultUser.username))
            .thenReturn(defaultUser)

        val user = userService.getUser(defaultUser.username)
        assertEquals(user, UserDTO(defaultUser.username, defaultUser.email, defaultUser.points, 0))
    }

    @Test
    fun `Get user returns null if user does not exist`() {
        Mockito.`when`(usersRepository.findByUsername(defaultUser.username))
            .thenReturn(null)

        assertThrows<NotFoundException> {
            userService.getUser(defaultUser.username)
        }
    }

    @Test
    fun `Create user`() {
        val users = mutableMapOf<Int, User>()
        val tokens = mutableMapOf<Int, RefreshToken>()

        var refreshTokenSequenceId = 1
        var userSequenceId = 1

        Mockito.`when`(usersRepository.existsByUsername(defaultUser.username))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail(defaultUser.email))
            .thenReturn(false)

        Mockito.`when`(usersRepository.save(Mockito.any(User::class.java)))
            .thenAnswer {
                val user = it.arguments[0] as User

                users[userSequenceId++] = user

                user
            }

        Mockito.`when`(refreshTokensRepository.save(Mockito.any(RefreshToken::class.java)))
            .thenAnswer {
                val token = it.arguments[0] as RefreshToken

                users.values.find { u -> u.username == token.user.username }
                    ?: throw NotFoundException("User not found")

                tokens[refreshTokenSequenceId++] = token

                token
            }

        val result = userService.register(
            RegisterUserInputDTO(
                username = defaultUser.username,
                email = defaultUser.email,
                password = "password"
            )
        )

        assertTrue(users.isNotEmpty())
        assertTrue(tokens.isNotEmpty())
        assertEquals(result.username, defaultUser.username)
    }
}
