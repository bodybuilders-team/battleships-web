package pt.isel.daw.battleships.services

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.AuthenticationException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.utils.JwtProvider

/**
 * Service that handles the authentication of the users.
 *
 * @property usersRepository the repository of the users
 * @property jwtProvider the JWT provider
 */
@Service
abstract class AuthenticatedService(
    protected val usersRepository: UsersRepository,
    protected val jwtProvider: JwtProvider
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
        val tokenPayload = jwtProvider.validateToken(token)
            ?: throw AuthenticationException("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username)
            ?: throw NotFoundException("User not found")
    }
}