package pt.isel.daw.battleships.service.users

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.domain.RefreshToken
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.repository.users.RefreshTokensRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
import pt.isel.daw.battleships.service.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.users.dtos.UserDTO
import pt.isel.daw.battleships.service.users.dtos.UsersDTO
import pt.isel.daw.battleships.service.users.dtos.login.LoginUserInputDTO
import pt.isel.daw.battleships.service.users.dtos.login.LoginUserOutputDTO
import pt.isel.daw.battleships.service.users.dtos.refreshToken.RefreshTokenOutputDTO
import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserInputDTO
import pt.isel.daw.battleships.service.users.dtos.register.RegisterUserOutputDTO
import pt.isel.daw.battleships.service.utils.HashingUtils
import pt.isel.daw.battleships.service.utils.OffsetPageRequest
import pt.isel.daw.battleships.utils.JwtProvider
import pt.isel.daw.battleships.utils.JwtProvider.JwtPayload
import pt.isel.daw.battleships.utils.ServerConfiguration
import java.time.Instant

/**
 * Service that handles the business logic of the users.
 *
 * @property usersRepository the repository of the users
 * @property refreshTokensRepository the repository of the refresh tokens
 * @property hashingUtils the utils for password operations
 * @property jwtProvider the JWT provider
 * @property config the server configuration
 */
@Service
@Transactional
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val refreshTokensRepository: RefreshTokensRepository,
    private val hashingUtils: HashingUtils,
    private val jwtProvider: JwtProvider,
    private val config: ServerConfiguration
) : UsersService {

    override fun getUsers(offset: Int, limit: Int, orderBy: UsersOrder, ascending: Boolean): UsersDTO {
        if (offset < 0 || limit < 0) {
            throw InvalidPaginationParamsException("Offset and limit must be positive")
        }

        if (limit > MAX_USERS_LIMIT) {
            throw InvalidPaginationParamsException("Limit must be less than $MAX_USERS_LIMIT")
        }

        return UsersDTO(
            users = usersRepository
                .let {
                    val pageable =
                        OffsetPageRequest(
                            offset = offset.toLong(),
                            limit = limit,
                            sort = orderBy.toSort(ascending)
                        )

                    usersRepository.findAll(/* pageable = */ pageable)
                }
                .toList()
                .map(::UserDTO),
            totalCount = usersRepository.count().toInt()
        )
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

        val (accessToken, refreshToken) = createTokens(user = user)

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
                passwordHash = user.passwordHash
            )
        ) throw NotFoundException("Invalid username or password")

        val (accessToken, refreshToken) = createTokens(user = user)

        return LoginUserOutputDTO(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun logout(refreshToken: String) {
        val user = getUserFromRefreshToken(refreshToken = refreshToken)
        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        if (!refreshTokensRepository.existsByUserAndTokenHash(user = user, tokenHash = refreshTokenHash)) {
            throw NotFoundException("Refresh token not found")
        }

        refreshTokensRepository.deleteByUserAndTokenHash(user = user, tokenHash = refreshTokenHash)
    }

    override fun refreshToken(refreshToken: String): RefreshTokenOutputDTO {
        val user = getUserFromRefreshToken(refreshToken = refreshToken)
        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        val refreshTokenEntity = refreshTokensRepository
            .findByUserAndTokenHash(
                user = user,
                tokenHash = refreshTokenHash
            )
            ?: throw NotFoundException("Refresh token not found")

        refreshTokensRepository.delete(refreshTokenEntity)

        if (refreshTokenEntity.expirationDate.isBefore(Instant.now())) {
            throw NotFoundException("Refresh token expired")
        }

        val (accessToken, newRefreshToken) = createTokens(user = refreshTokenEntity.user)

        return RefreshTokenOutputDTO(
            accessToken = accessToken,
            refreshToken = newRefreshToken
        )
    }

    override fun getUser(username: String): UserDTO {
        val user = usersRepository
            .findByUsername(username)
            ?: throw NotFoundException("User with username $username not found")

        return UserDTO(user = user)
    }

    /**
     * Creates the access and refresh tokens for the given user.
     *
     * @param user the user to create the tokens for
     *
     * @return the access and refresh tokens
     * @throws IllegalStateException if the user has no refresh tokens
     */
    private fun createTokens(user: User): Tokens {
        if (refreshTokensRepository.countByUser(user = user) >= config.maxRefreshTokens) {
            refreshTokensRepository
                .getRefreshTokensOfUserOrderedByExpirationDate(
                    user = user,
                    pageable = PageRequest.of(/* page = */ 0, /* size = */ 1)
                )
                .get()
                .findFirst()
                .ifPresent { refreshTokensRepository.delete(it) }
        }

        val jwtPayload = JwtPayload(username = user.username)
        val accessToken = jwtProvider.createAccessToken(jwtPayload = jwtPayload)
        val (refreshToken, expirationDate) = jwtProvider.createRefreshToken(jwtPayload = jwtPayload)

        val refreshTokenHash = hashingUtils.hashToken(token = refreshToken)

        refreshTokensRepository.save(
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
        val tokenPayload = jwtProvider.validateRefreshToken(token = refreshToken)
            ?: throw AuthenticationException("Invalid token")

        return usersRepository.findByUsername(username = tokenPayload.username)
            ?: throw NotFoundException("User not found")
    }

    companion object {
        const val MAX_USERS_LIMIT = 100
    }
}
