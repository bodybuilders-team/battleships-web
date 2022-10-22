package pt.isel.daw.battleships.services.games

import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.battleships.domain.RefreshToken
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.repository.RefreshTokenRepository
import pt.isel.daw.battleships.repository.UsersRepository
import pt.isel.daw.battleships.dtos.users.UserDTO
import pt.isel.daw.battleships.dtos.users.register.RegisterUserInputDTO
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.users.UsersService
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTests {
    @MockBean
    lateinit var usersRepository: UsersRepository

    @MockBean
    lateinit var refreshTokenRepository: RefreshTokenRepository

    @Autowired
    lateinit var userService: UsersService

    @Test
    fun `Get user works`() {
        Mockito.`when`(usersRepository.findByUsername("bob"))
            .thenReturn(User("bob", "bob@bob.com", "password"))

        val user = userService.getUser("bob")
        assertEquals(user, UserDTO("bob", "bob@bob.com", 0))
    }

    @Test
    fun `Get user returns null if user does not exist`() {
        Mockito.`when`(usersRepository.findByUsername("bob"))
            .thenReturn(null)

        assertThrows<NotFoundException> {
            userService.getUser("bob")
        }
    }

    @Test
    fun `Create user`() {
        val users = mutableMapOf<Int, User>()
        val tokens = mutableMapOf<Int, RefreshToken>()

        var refreshTokenSequenceId = 1
        var userSequenceId = 1

        Mockito.`when`(usersRepository.existsByUsername("bob"))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail("bob@bob.com"))
            .thenReturn(false)

        Mockito.`when`(usersRepository.save(Mockito.any(User::class.java)))
            .thenAnswer {
                val u = it.arguments[0] as User
                u.id = userSequenceId
                users[userSequenceId++] = u

                u
            }

        Mockito.`when`(refreshTokenRepository.save(Mockito.any(RefreshToken::class.java)))
            .thenAnswer {
                val token = it.arguments[0] as RefreshToken

                users.values.find { u -> u.username == token.user.username }
                    ?: throw NotFoundException("User not found")

                token.id = refreshTokenSequenceId

                tokens[refreshTokenSequenceId++] = token

                token
            }

        val result = userService.register(RegisterUserInputDTO("bob", "bob@bob.com", "password"))

        assertTrue(users.isNotEmpty())
        assertTrue(tokens.isNotEmpty())
        assertEquals(result.username, "bob")
    }
}
