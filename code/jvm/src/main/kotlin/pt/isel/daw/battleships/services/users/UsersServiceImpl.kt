package pt.isel.daw.battleships.services.users

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.RefreshToken
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.repositories.RefreshTokenRepository
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.dtos.users.UserDTO
import pt.isel.daw.battleships.dtos.users.UsersDTO
import pt.isel.daw.battleships.dtos.users.createUser.CreateUserInputDTO
import pt.isel.daw.battleships.dtos.users.createUser.CreateUserOutputDTO
import pt.isel.daw.battleships.dtos.users.login.LoginUserInputDTO
import pt.isel.daw.battleships.dtos.users.login.LoginUserOutputDTO
import pt.isel.daw.battleships.dtos.users.refresh.RefreshTokenOutputDTO
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
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the users.
 *
 * @property usersRepository the repository of the users
 * @property hashingUtils the utils for password operations
 * @property jwtProvider the JWT provider
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

    override fun createUser(createUserInputDTO: CreateUserInputDTO): CreateUserOutputDTO {
        if (usersRepository.existsByUsername(username = createUserInputDTO.username)) {
            throw AlreadyExistsException("User with username ${createUserInputDTO.username} already exists")
        }

        if (usersRepository.existsByEmail(email = createUserInputDTO.email)) {
            throw AlreadyExistsException("User with email ${createUserInputDTO.email} already exists")
        }

        val user = User(
            username = createUserInputDTO.username,
            email = createUserInputDTO.email,
            hashedPassword = hashingUtils.hashPassword(
                username = createUserInputDTO.username,
                password = createUserInputDTO.password
            )
        )

        usersRepository.save(user)

        val (accessToken, refreshToken) = createTokens(user)

        return CreateUserOutputDTO(
            username = createUserInputDTO.username,
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
                hashedPassword = user.hashedPassword
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

        val cipheredRefreshToken = hashingUtils.hashToken(refreshToken)

        if (!refreshTokenRepository.existsByUserAndTokenHash(user = user, tokenHash = cipheredRefreshToken)) {
            throw NotFoundException("Refresh token not found")
        }

        refreshTokenRepository.deleteByUserAndTokenHash(user = user, tokenHash = cipheredRefreshToken)
    }

    override fun refreshToken(refreshToken: String): RefreshTokenOutputDTO {
        val user = getUserFromRefreshToken(refreshToken)

        val refreshTokenHash = hashingUtils.hashToken(refreshToken)

        val refreshTokenEntity = refreshTokenRepository.findByUserAndTokenHash(
            user = user,
            tokenHash = refreshTokenHash
        )
            ?: throw NotFoundException("Refresh token not found")

        refreshTokenRepository.delete(refreshTokenEntity)

        if (refreshTokenEntity.expirationDate.isAfter(Instant.now())) {
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

    private fun createTokens(user: User): Tokens {
        if (refreshTokenRepository.countByUser(user) >= config.maxRefreshTokens) {
            refreshTokenRepository.getOldestRefreshTokensByUser(
                user = user,
                pageable = PageRequest.of(0, 1)
            )
                .get()
                .findFirst()
                .ifPresentOrElse(
                    { refreshTokenRepository.delete(it) }
                ) { throw IllegalStateException("User with username ${user.username} has no refresh tokens") }
        }

        val jwtPayload = JwtPayload(user.username)

        val accessToken = jwtProvider.createToken(jwtPayload)
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

    data class Tokens(val accessToken: String, val refreshToken: String)

    private fun getUserFromRefreshToken(token: String): User {
        val tokenPayload = jwtProvider.validateRefreshToken(token)
            ?: throw AuthenticationException("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username)
            ?: throw NotFoundException("User not found")
    }

    companion object {
        const val MAX_USERS_LIMIT = 100
    }
}
