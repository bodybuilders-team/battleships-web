package pt.isel.daw.battleships.service.users

import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import pt.isel.daw.battleships.domain.users.RefreshToken
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.repository.users.RefreshTokensRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
import pt.isel.daw.battleships.service.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.InvalidLoginException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.exceptions.RefreshTokenExpiredException
import pt.isel.daw.battleships.service.users.dtos.UserDTO
import pt.isel.daw.battleships.service.users.dtos.UserDTOTests.Companion.defaultUserDTO
import pt.isel.daw.battleships.service.users.dtos.UsersDTO
import pt.isel.daw.battleships.service.users.dtos.login.LoginInputDTO
import pt.isel.daw.battleships.service.users.dtos.register.RegisterInputDTO
import pt.isel.daw.battleships.service.users.utils.UsersOrder
import pt.isel.daw.battleships.service.utils.HashingUtils
import pt.isel.daw.battleships.service.utils.OffsetPageRequest
import pt.isel.daw.battleships.utils.JwtProvider
import pt.isel.daw.battleships.utils.JwtProvider.Companion.refreshTokenDuration
import pt.isel.daw.battleships.utils.ServerConfiguration
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTests {
    @MockBean
    lateinit var usersRepository: UsersRepository

    @MockBean
    lateinit var refreshTokensRepository: RefreshTokensRepository

    @Autowired
    lateinit var hashingUtils: HashingUtils

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var config: ServerConfiguration

    @Autowired
    lateinit var userService: UsersService

    @Test
    fun `getUsers returns a UsersDTO if there's only one user`() {
        val offset = 0
        val limit = 5
        val orderBy = UsersOrder.POINTS
        val ascending = true

        Mockito.`when`(
            usersRepository.findAll(
                OffsetPageRequest(
                    offset = offset.toLong(),
                    limit = limit,
                    sort = orderBy.toSort(ascending)
                )
            )
        ).thenReturn(
            PageImpl(listOf(defaultUser(0)))
        )

        Mockito.`when`(usersRepository.count()).thenReturn(1)

        val users = userService.getUsers(
            offset = offset,
            limit = limit,
            orderBy = orderBy,
            ascending = ascending
        )
        assertEquals(
            UsersDTO(
                users = listOf(defaultUserDTO(0)),
                totalCount = 1
            ),
            users
        )
    }

    @Test
    fun `getUsers returns a UsersDTO if there are multiple users`() {
        val offset = 0
        val limit = 5
        val orderBy = UsersOrder.POINTS
        val ascending = true

        Mockito.`when`(
            usersRepository.findAll(
                OffsetPageRequest(
                    offset = offset.toLong(),
                    limit = limit,
                    sort = orderBy.toSort(ascending)
                )
            )
        ).thenReturn(
            PageImpl(
                listOf(
                    defaultUser(0),
                    defaultUser(1),
                    defaultUser(2)
                )
            )
        )

        Mockito.`when`(usersRepository.count()).thenReturn(3)

        val users = userService.getUsers(
            offset = offset,
            limit = limit,
            orderBy = orderBy,
            ascending = ascending
        )
        assertEquals(
            UsersDTO(
                users = listOf(
                    defaultUserDTO(0),
                    defaultUserDTO(1),
                    defaultUserDTO(2)
                ),
                totalCount = 3
            ),
            users
        )
    }

    @Test
    fun `getUsers returns total count doesn't need to be the same as returned users count because of pagination`() {
        val offset = 0
        val limit = 2
        val orderBy = UsersOrder.POINTS
        val ascending = true

        Mockito.`when`(
            usersRepository.findAll(
                OffsetPageRequest(
                    offset = offset.toLong(),
                    limit = limit,
                    sort = orderBy.toSort(ascending)
                )
            )
        ).thenReturn(
            PageImpl(
                listOf(
                    defaultUser(0),
                    defaultUser(1)
                )
            )
        )

        Mockito.`when`(usersRepository.count()).thenReturn(3)

        val users = userService.getUsers(
            offset = offset,
            limit = limit,
            orderBy = orderBy,
            ascending = ascending
        )
        assertEquals(
            UsersDTO(
                users = listOf(
                    defaultUserDTO(0),
                    defaultUserDTO(1)
                ),
                totalCount = 3
            ),
            users
        )
    }

    @Test
    fun `getUsers throws InvalidPaginationParamsException if the offset is negative`() {
        assertFailsWith<InvalidPaginationParamsException> {
            userService.getUsers(
                offset = -1,
                limit = 0,
                orderBy = UsersOrder.POINTS,
                ascending = true
            )
        }
    }

    @Test
    fun `getUsers throws InvalidPaginationParamsException if the limit is negative`() {
        assertFailsWith<InvalidPaginationParamsException> {
            userService.getUsers(
                offset = 0,
                limit = -1,
                orderBy = UsersOrder.POINTS,
                ascending = true
            )
        }
    }

    @Test
    fun `getUsers throws InvalidPaginationParamsException if the limit is higher than max users limit`() {
        assertFailsWith<InvalidPaginationParamsException> {
            userService.getUsers(
                offset = 0,
                limit = 101,
                orderBy = UsersOrder.POINTS,
                ascending = true
            )
        }
    }

    @Test
    fun `register creates a new user in the database with the correct information`() {
        val users = mutableMapOf<Int, User>()

        val username = defaultUser(0).username
        val email = defaultUser(0).email
        val password = "password"

        Mockito.`when`(usersRepository.existsByUsername(username))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail(email))
            .thenReturn(false)

        Mockito.`when`(usersRepository.save(Mockito.any(User::class.java)))
            .thenAnswer {
                val user = it.arguments[0] as User

                users[1] = user

                user
            }

        userService.register(
            RegisterInputDTO(
                username = username,
                email = email,
                password = password
            )
        )

        val savedUser = users[1]

        assertTrue(users.isNotEmpty())
        assertNotNull(savedUser)
        assertEquals(username, savedUser.username)
        assertEquals(email, savedUser.email)
        assertEquals(hashingUtils.hashPassword(username, password), savedUser.passwordHash)
        assertEquals(0, savedUser.points)
        assertEquals(0, savedUser.numberOfGamesPlayed)
    }

    @Test
    fun `register creates a new refresh token in the database for the user`() {
        val users = mutableMapOf<Int, User>()
        val refreshTokens = mutableMapOf<Int, RefreshToken>()

        val username = defaultUser(0).username
        val email = defaultUser(0).email

        Mockito.`when`(usersRepository.existsByUsername(username))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail(email))
            .thenReturn(false)

        Mockito.`when`(usersRepository.save(Mockito.any(User::class.java)))
            .thenAnswer {
                val user = it.arguments[0] as User

                users[1] = user

                user
            }

        Mockito.`when`(refreshTokensRepository.save(Mockito.any(RefreshToken::class.java)))
            .thenAnswer {
                val token = it.arguments[0] as RefreshToken

                refreshTokens[1] = token

                token
            }

        val expirationDateInstantIfBefore = Instant.now().plus(refreshTokenDuration)

        userService.register(
            RegisterInputDTO(
                username = username,
                email = email,
                password = "password"
            )
        )
        val expirationDateInstantIfAfter = Instant.now().plus(refreshTokenDuration)

        val savedUser = users[1]
        val savedRefreshToken = refreshTokens[1]

        assertTrue(refreshTokens.isNotEmpty())
        assertNotNull(savedRefreshToken)
        assertNotNull(savedUser)
        assertEquals(savedUser, savedRefreshToken.user)

        val expirationDateInstant = savedRefreshToken.expirationDate.toInstant()

        assertTrue(
            expirationDateInstantIfBefore == expirationDateInstant ||
                expirationDateInstantIfBefore.isBefore(expirationDateInstant)
        )
        assertTrue(
            expirationDateInstantIfAfter == expirationDateInstant ||
                expirationDateInstantIfAfter.isAfter(expirationDateInstant)
        )

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = username)
        ).token
        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)
        assertEquals(refreshTokenHash, savedRefreshToken.tokenHash)
    }

    @Test
    fun `register deletes the oldest refresh token if new one exceeds max refresh tokens`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email
        val user = defaultUser(0)

        var deletedOld = false

        Mockito.`when`(usersRepository.existsByUsername(username))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail(email))
            .thenReturn(false)

        Mockito.`when`(usersRepository.save(Mockito.any(User::class.java)))
            .thenReturn(user)

        Mockito.`when`(refreshTokensRepository.countByUser(user = user))
            .thenReturn(config.maxRefreshTokens)

        val page = PageImpl(
            listOf(
                RefreshToken(
                    user = user,
                    tokenHash = "a".repeat(128),
                    expirationDate = Timestamp.from(Instant.now().minusSeconds(1))
                )
            )
        )

        Mockito.`when`(
            refreshTokensRepository.getRefreshTokensOfUserOrderedByExpirationDate(
                user = user,
                pageable = PageRequest.of(/* page = */ 0, /* size = */ 1)
            )
        ).thenReturn(page)

        Mockito.`when`(page.get().findFirst().ifPresent { refreshTokensRepository.delete(it) })
            .thenAnswer {
                deletedOld = true

                null
            }

        userService.register(
            RegisterInputDTO(
                username = username,
                email = email,
                password = "password"
            )
        )

        assertTrue(deletedOld)
    }

    @Test
    fun `register returns a RegisterUserOutputDTO with the correct information`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email

        Mockito.`when`(usersRepository.existsByUsername(username))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail(email))
            .thenReturn(false)

        Mockito.`when`(usersRepository.save(Mockito.any(User::class.java)))
            .thenAnswer {
                it.arguments[0] as User
            }

        val registerUserOutputDTO = userService.register(
            RegisterInputDTO(
                username = username,
                email = email,
                password = "password"
            )
        )

        val jwtPayload = JwtProvider.JwtPayload(username = username)

        assertEquals(username, registerUserOutputDTO.username)
        assertEquals(jwtPayload, jwtProvider.validateAccessToken(token = registerUserOutputDTO.accessToken))
        assertEquals(jwtPayload, jwtProvider.validateRefreshToken(token = registerUserOutputDTO.refreshToken))
    }

    @Test
    fun `register throws AlreadyExistsException if a user with the username already exists`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email

        Mockito.`when`(usersRepository.existsByUsername(username))
            .thenReturn(true)

        Mockito.`when`(usersRepository.existsByEmail(email))
            .thenReturn(false)

        assertFailsWith<AlreadyExistsException> {
            userService.register(
                RegisterInputDTO(
                    username = username,
                    email = email,
                    password = "password"
                )
            )
        }
    }

    @Test
    fun `register throws AlreadyExistsException if a user with the email already exists`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email

        Mockito.`when`(usersRepository.existsByUsername(username))
            .thenReturn(false)

        Mockito.`when`(usersRepository.existsByEmail(email))
            .thenReturn(true)

        assertFailsWith<AlreadyExistsException> {
            userService.register(
                RegisterInputDTO(
                    username = username,
                    email = email,
                    password = "password"
                )
            )
        }
    }

    @Test
    fun `login returns a LoginUserOutputDTO with the correct information`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email
        val password = "password"

        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(
                User(
                    username = username,
                    email = email,
                    passwordHash = hashingUtils.hashPassword(username, password),
                    points = 0,
                    numberOfGamesPlayed = 0
                )
            )

        val loginUserOutputDTO = userService.login(
            LoginInputDTO(
                username = username,
                password = password
            )
        )

        val jwtPayload = JwtProvider.JwtPayload(username = username)

        assertEquals(jwtPayload, jwtProvider.validateAccessToken(token = loginUserOutputDTO.accessToken))
        assertEquals(jwtPayload, jwtProvider.validateRefreshToken(token = loginUserOutputDTO.refreshToken))
    }

    @Test
    fun `login deletes the oldest refresh token if new one exceeds max refresh tokens`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email
        val password = "password"
        val user = User(
            username = username,
            email = email,
            passwordHash = hashingUtils.hashPassword(username, password),
            points = 0,
            numberOfGamesPlayed = 0
        )

        var deletedOld = false

        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(user)

        Mockito.`when`(refreshTokensRepository.countByUser(user = user))
            .thenReturn(config.maxRefreshTokens)

        val page = PageImpl(
            listOf(
                RefreshToken(
                    user = user,
                    tokenHash = "a".repeat(128),
                    expirationDate = Timestamp.from(Instant.now().minusSeconds(1))
                )
            )
        )

        Mockito.`when`(
            refreshTokensRepository.getRefreshTokensOfUserOrderedByExpirationDate(
                user = user,
                pageable = PageRequest.of(/* page = */ 0, /* size = */ 1)
            )
        ).thenReturn(page)

        Mockito.`when`(page.get().findFirst().ifPresent { refreshTokensRepository.delete(it) })
            .thenAnswer {
                deletedOld = true

                null
            }

        userService.login(
            LoginInputDTO(
                username = username,
                password = password
            )
        )

        assertTrue(deletedOld)
    }

    @Test
    fun `login throws a NotFoundException if a user with the username doesn't exist`() {
        val username = defaultUser(0).username
        val password = "password"

        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(null)

        assertFailsWith<NotFoundException> {
            userService.login(
                LoginInputDTO(
                    username = username,
                    password = password
                )
            )
        }
    }

    @Test
    fun `login throws an InvalidLoginException if the password is incorrect`() {
        val username = defaultUser(0).username
        val email = defaultUser(0).email
        val password = "password"

        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(
                User(
                    username = username,
                    email = email,
                    passwordHash = hashingUtils.hashPassword(username, password),
                    points = 0,
                    numberOfGamesPlayed = 0
                )
            )

        assertFailsWith<InvalidLoginException> {
            userService.login(
                LoginInputDTO(
                    username = username,
                    password = "wrongPassword"
                )
            )
        }
    }

    @Test
    fun `logout deletes the refresh token`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = refreshTokenHash,
            expirationDate = Timestamp.from(Instant.now().plus(refreshTokenDuration))
        )

        var deleted = false

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(refreshTokenEntity)

        Mockito.`when`(refreshTokensRepository.delete(refreshTokenEntity))
            .then {
                deleted = true
                null
            }

        userService.logout(refreshToken)

        assertTrue(deleted)
    }

    @Test
    fun `logout throws AuthenticationException if the refresh token is invalid`() {
        val refreshToken = "invalidRefreshToken"

        assertFailsWith<AuthenticationException> {
            userService.logout(refreshToken)
        }
    }

    @Test
    fun `logout throws NotFoundException if the user with the username in the refresh token doesn't exist`() {
        val username = defaultUser(0).username

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = username)
        ).token

        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(null)

        assertFailsWith<NotFoundException> {
            userService.logout(refreshToken)
        }
    }

    @Test
    fun `logout throws NotFoundException if the refresh token wasn't found`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(null)

        assertFailsWith<NotFoundException> {
            userService.logout(refreshToken)
        }
    }

    @Test
    fun `refreshToken creates a new refresh token in the database`() {
        val refreshTokens = mutableMapOf<Int, RefreshToken>()

        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = refreshTokenHash,
            expirationDate = Timestamp.from(Instant.now().plus(refreshTokenDuration))
        )

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(refreshTokenEntity)

        Mockito.`when`(refreshTokensRepository.save(Mockito.any(RefreshToken::class.java)))
            .then {
                val token = it.arguments[0] as RefreshToken

                refreshTokens[1] = token

                token
            }

        val expirationDateInstantIfBefore = Instant.now().plus(refreshTokenDuration)

        userService.refreshToken(refreshToken)

        val expirationDateInstantIfAfter = Instant.now().plus(refreshTokenDuration)

        val savedRefreshToken = refreshTokens[1]

        assertTrue(refreshTokens.isNotEmpty())
        assertNotNull(savedRefreshToken)
        assertEquals(user, savedRefreshToken.user)

        val expirationDateInstant = savedRefreshToken.expirationDate.toInstant()
        assertTrue(
            expirationDateInstantIfBefore == expirationDateInstant ||
                expirationDateInstantIfBefore.isBefore(expirationDateInstant)
        )
        assertTrue(
            expirationDateInstantIfAfter == expirationDateInstant ||
                expirationDateInstantIfAfter.isAfter(expirationDateInstant)
        )

        val newRefreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token
        val newRefreshTokenHash = hashingUtils.hashToken(token = newRefreshToken)
        assertEquals(newRefreshTokenHash, savedRefreshToken.tokenHash)
    }

    @Test
    fun `refreshToken returns a RefreshTokenOutputDTO with the correct information`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = refreshTokenHash,
            expirationDate = Timestamp.from(Instant.now().plus(refreshTokenDuration))
        )

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(refreshTokenEntity)

        val refreshTokenOutputDTO = userService.refreshToken(refreshToken)

        val jwtPayload = JwtProvider.JwtPayload(username = user.username)

        assertEquals(jwtPayload, jwtProvider.validateAccessToken(token = refreshTokenOutputDTO.accessToken))
        assertEquals(jwtPayload, jwtProvider.validateRefreshToken(token = refreshTokenOutputDTO.refreshToken))
    }

    @Test
    fun `refreshToken deletes the old refresh token`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = refreshTokenHash,
            expirationDate = Timestamp.from(Instant.now().plus(refreshTokenDuration))
        )

        var deleted = false

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(refreshTokenEntity)

        Mockito.`when`(refreshTokensRepository.delete(refreshTokenEntity))
            .then {
                deleted = true
                null
            }

        userService.refreshToken(refreshToken)

        assertTrue(deleted)
    }

    @Test
    fun `refreshToken the oldest refresh token if new one exceeds max refresh tokens`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = refreshTokenHash,
            expirationDate = Timestamp.from(Instant.now().plus(refreshTokenDuration))
        )

        var deletedOld = false

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(refreshTokenEntity)

        Mockito.`when`(refreshTokensRepository.countByUser(user = user))
            .thenReturn(config.maxRefreshTokens)

        val page = PageImpl(
            listOf(
                RefreshToken(
                    user = user,
                    tokenHash = "a".repeat(128),
                    expirationDate = Timestamp.from(Instant.now().minusSeconds(1))
                )
            )
        )

        Mockito.`when`(
            refreshTokensRepository.getRefreshTokensOfUserOrderedByExpirationDate(
                user = user,
                pageable = PageRequest.of(/* page = */ 0, /* size = */ 1)
            )
        ).thenReturn(page)

        Mockito.`when`(page.get().findFirst().ifPresent { refreshTokensRepository.delete(it) })
            .thenAnswer {
                deletedOld = true

                null
            }

        userService.refreshToken(refreshToken)

        assertTrue(deletedOld)
    }

    @Test
    fun `refreshToken throws AuthenticationException if the refresh token is invalid`() {
        val refreshToken = "invalidRefreshToken"

        assertFailsWith<AuthenticationException> {
            userService.refreshToken(refreshToken)
        }
    }

    @Test
    fun `refreshToken throws NotFoundException if the user with the username in the refresh token doesn't exist`() {
        val username = defaultUser(0).username

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = username)
        ).token

        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(null)

        assertFailsWith<NotFoundException> {
            userService.refreshToken(refreshToken)
        }
    }

    @Test
    fun `refreshToken throws NotFoundException if the refresh token wasn't found`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(null)

        assertFailsWith<NotFoundException> {
            userService.refreshToken(refreshToken)
        }
    }

    @Test
    fun `refreshToken throws RefreshTokenExpiredException if the refresh token has expired`() {
        val user = defaultUser(0)

        val refreshToken = jwtProvider.createRefreshToken(
            jwtPayload = JwtProvider.JwtPayload(username = user.username)
        ).token

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = refreshTokenHash,
            expirationDate = Timestamp.from(Instant.now().minusSeconds(1))
        )

        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        Mockito.`when`(
            refreshTokensRepository
                .findByUserAndTokenHash(
                    user = user,
                    tokenHash = refreshTokenHash
                )
        ).thenReturn(refreshTokenEntity)

        assertFailsWith<RefreshTokenExpiredException> {
            userService.refreshToken(refreshToken)
        }
    }

    @Test
    fun `getUser returns the user`() {
        val user = defaultUser(0)
        Mockito.`when`(usersRepository.findByUsername(user.username))
            .thenReturn(user)

        val userDTO = userService.getUser(user.username)
        assertEquals(UserDTO(user = user), userDTO)
    }

    @Test
    fun `getUser throws NotFoundException if user does not exist`() {
        val username = "username"
        Mockito.`when`(usersRepository.findByUsername(username))
            .thenReturn(null)

        assertThrows<NotFoundException> {
            userService.getUser(username)
        }
    }
}
