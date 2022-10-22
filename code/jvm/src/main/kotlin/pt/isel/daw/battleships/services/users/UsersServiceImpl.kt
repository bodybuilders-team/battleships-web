package pt.isel.daw.battleships.services.users

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.domain.RefreshToken
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.repository.RefreshTokenRepository
import pt.isel.daw.battleships.repository.UsersRepository
import pt.isel.daw.battleships.services.users.dtos.UserDTO
import pt.isel.daw.battleships.services.users.dtos.UsersDTO
import pt.isel.daw.battleships.services.users.dtos.login.LoginUserInputDTO
import pt.isel.daw.battleships.services.users.dtos.login.LoginUserOutputDTO
import pt.isel.daw.battleships.services.users.dtos.refresh.RefreshTokenOutputDTO
import pt.isel.daw.battleships.services.users.dtos.register.RegisterUserInputDTO
import pt.isel.daw.battleships.services.users.dtos.register.RegisterUserOutputDTO
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.AuthenticationException
import pt.isel.daw.battleships.services.exceptions.InvalidPaginationParams
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.utils.HashingUtils
import pt.isel.daw.battleships.services.utils.OffsetPageRequest
import pt.isel.daw.battleships.utils.JwtProvider
import pt.isel.daw.battleships.utils.JwtProvider.JwtPayload
import pt.isel.daw.battleships.utils.ServerConfiguration
import java.time.Instant

/**
 * Service that handles the business logic of the users.
 *
 * @property usersRepository the repository of the users
 * @property refreshTokenRepository the repository of the refresh tokens
 * @property hashingUtils the utils for password operations
 * @property jwtProvider the JWT provider
 * @property config the server configuration
 */
@Service
@Transactional
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val hashingUtils: HashingUtils,
    private val jwtProvider: JwtProvider,
    private val config: ServerConfiguration
) : UsersService {

    override fun getUsers(offset: Int, limit: Int): UsersDTO {
        if (offset < 0 || limit < 0) {
            throw InvalidPaginationParams("Offset and limit must be positive")
        }

        if (limit > MAX_USERS_LIMIT) {
            throw InvalidPaginationParams("Limit must be less than $MAX_USERS_LIMIT")
        }

        return usersRepository
            .findAll(OffsetPageRequest(offset.toLong(), limit))
            .toList()
            .map { UserDTO(it) }
            .let { users ->
                UsersDTO(
                    users = users,
                    totalCount = usersRepository.count().toInt()
                )
            }
    }

    override fun register(registerUserInputDTO: RegisterUserInputDTO): RegisterUserOutputDTO {
        if (usersRepository.existsByUsername(username = registerUserInputDTO.username)) {
            throw AlreadyExistsException("User with username ${registerUserInputDTO.username} already exists")
        }

        if (usersRepository.existsByEmail(email = registerUserInputDTO.email)) {
            throw AlreadyExistsException("User with email ${registerUserInputDTO.email} already exists")
        }

        val user = User(
            username = registerUserInputDTO.username,
            email = registerUserInputDTO.email,
            passwordHash = hashingUtils.hashPassword(
                username = registerUserInputDTO.username,
                password = registerUserInputDTO.password
            )
        )

        usersRepository.save(user)

        val (accessToken, refreshToken) = createTokens(user)

        return RegisterUserOutputDTO(
            username = registerUserInputDTO.username,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun login(loginUserInputDTO: LoginUserInputDTO): LoginUserOutputDTO {
        val user = usersRepository
            .findByUsername(username = loginUserInputDTO.username)
            ?: throw NotFoundException("User with username ${loginUserInputDTO.username} not found")

        if (
            !hashingUtils.checkPassword(
                username = loginUserInputDTO.username,
                password = loginUserInputDTO.password,
                hashedPassword = user.passwordHash
            )
        ) throw NotFoundException("Invalid username or password")

        val (accessToken, refreshToken) = createTokens(user)

        return LoginUserOutputDTO(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun logout(refreshToken: String) {
        val user = getUserFromRefreshToken(refreshToken)

        val refreshTokenHash = hashingUtils.hashToken(refreshToken)

        if (!refreshTokenRepository.existsByUserAndTokenHash(user = user, tokenHash = refreshTokenHash)) {
            throw NotFoundException("Refresh token not found")
        }

        refreshTokenRepository.deleteByUserAndTokenHash(user = user, tokenHash = refreshTokenHash)
    }

    override fun refreshToken(refreshToken: String): RefreshTokenOutputDTO {
        val user = getUserFromRefreshToken(refreshToken)

        val refreshTokenHash = hashingUtils.hashToken(refreshToken)

        val refreshTokenEntity = refreshTokenRepository
            .findByUserAndTokenHash(
                user = user,
                tokenHash = refreshTokenHash
            )
            ?: throw NotFoundException("Refresh token not found")

        refreshTokenRepository.delete(refreshTokenEntity)

        if (refreshTokenEntity.expirationDate.isBefore(Instant.now())) {
            throw NotFoundException("Refresh token expired")
        }

        val (accessToken, newRefreshToken) = createTokens(refreshTokenEntity.user)

        return RefreshTokenOutputDTO(
            accessToken = accessToken,
            refreshToken = newRefreshToken
        )
    }

    override fun getUser(username: String): UserDTO =
        UserDTO(
            user = usersRepository
                .findByUsername(username)
                ?: throw NotFoundException("User with username $username not found")
        )

    /**
     * Creates the access and refresh tokens for the given user.
     *
     * @param user the user to create the tokens for
     *
     * @return the access and refresh tokens
     * @throws IllegalStateException if the user has no refresh tokens
     */
    private fun createTokens(user: User): Tokens {
        if (refreshTokenRepository.countByUser(user) >= config.maxRefreshTokens) {
            refreshTokenRepository
                .getOldestRefreshTokensByUser(
                    user = user,
                    pageable = PageRequest.of(/* page = */ 0, /* size = */ 1)
                )
                .get()
                .findFirst()
                .ifPresent { refreshTokenRepository.delete(it) }
        }

        val jwtPayload = JwtPayload(user.username)
        val accessToken = jwtProvider.createAccessToken(jwtPayload)
        val (refreshToken, expirationDate) = jwtProvider.createRefreshToken(jwtPayload)

        val refreshTokenHash = hashingUtils.hashToken(refreshToken)

        refreshTokenRepository.save(
            RefreshToken(
                user = user,
                tokenHash = refreshTokenHash,
                expirationDate = expirationDate
            )
        )

        return Tokens(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    /**
     * Represents the tokens of a user.
     *
     * @property accessToken the access token
     * @property refreshToken the refresh token
     */
    private data class Tokens(
        val accessToken: String,
        val refreshToken: String
    )

    /**
     * Gets the user from the refresh token.
     *
     * @param refreshToken the refresh token
     *
     * @return the user
     * @throws AuthenticationException if the refresh token is invalid
     * @throws NotFoundException if a user with the refresh token was not found
     */
    private fun getUserFromRefreshToken(refreshToken: String): User {
        val tokenPayload = jwtProvider.validateRefreshToken(refreshToken)
            ?: throw AuthenticationException("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username)
            ?: throw NotFoundException("User not found")
    }

    companion object {
        const val MAX_USERS_LIMIT = 100
    }
}
