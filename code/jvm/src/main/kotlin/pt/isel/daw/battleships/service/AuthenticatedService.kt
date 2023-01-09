package pt.isel.daw.battleships.service

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.repository.users.RevokedAccessTokensRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.utils.HashingUtils
import pt.isel.daw.battleships.utils.JwtProvider

/**
 * Service that handles the authentication of the users.
 *
 * @property usersRepository the repository of the users
 * @property jwtProvider the JWT provider
 */
@Service
abstract class AuthenticatedService(
    private val usersRepository: UsersRepository,
    private val revokedAccessTokensRepository: RevokedAccessTokensRepository,
    private val jwtProvider: JwtProvider,
    private val hashingUtils: HashingUtils
) {

    /**
     * Authenticates a user.
     *
     * @param token the token of the user
     *
     * @return the authenticated user
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     */
    fun authenticateUser(token: String): User {
        val tokenPayload = jwtProvider.validateAccessToken(token)
            ?: throw AuthenticationException("Invalid token")

        val user = usersRepository.findByUsername(tokenPayload.username)
            ?: throw NotFoundException("User not found")

        if (revokedAccessTokensRepository.existsByUserAndTokenHash(user, hashingUtils.hashToken(token)))
            throw AuthenticationException("Token is revoked")

        return user
    }
}
