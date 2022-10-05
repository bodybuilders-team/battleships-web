package pt.isel.daw.battleships.services.users

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.users.dtos.CreateUserRequestDTO
import pt.isel.daw.battleships.services.users.dtos.UserDTO
import pt.isel.daw.battleships.utils.JwtProvider
import pt.isel.daw.battleships.utils.JwtProvider.JwtPayload
import pt.isel.daw.battleships.utils.Utils
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the users.
 *
 * @property usersRepository the repository of the users
 * @property utils the utils
 * @property jwtProvider the JWT provider
 */
@Service
@Transactional
class UsersService(
    private val usersRepository: UsersRepository,
    private val utils: Utils,
    private val jwtProvider: JwtProvider
) {

    /**
     * Creates a new user.
     *
     * @param createUserRequestDTO the DTO with the data to create the user
     *
     * @return the JWT token for the new user
     * @throws AlreadyExistsException if the user already exists
     */
    fun createUser(createUserRequestDTO: CreateUserRequestDTO): String {
        if (usersRepository.existsByUsername(createUserRequestDTO.username))
            throw AlreadyExistsException("User with username ${createUserRequestDTO.username} already exists")

        val hashedPassword = utils.hashPassword(createUserRequestDTO.password)
        val user = User(createUserRequestDTO.username, hashedPassword)

        usersRepository.save(user)

        return jwtProvider.createToken(JwtPayload(createUserRequestDTO.username))
    }

    /**
     * Logs a user in.
     *
     * @param username the username of the user
     * @param password the password of the user
     *
     * @return the JWT token for the user
     * @throws NotFoundException if the user does not exist or the password is incorrect
     */
    fun login(username: String, password: String): String {
        val user = usersRepository.findByUsername(username)
            ?: throw NotFoundException("User with username $username not found")

        if (!utils.checkPassword(password, user.hashedPassword))
            throw NotFoundException("Invalid username or password")

        return jwtProvider.createToken(JwtPayload(username))
    }

    /**
     * Gets the user with the given username.
     *
     * @param username the username of the user
     *
     * @return the DTO with the user's data
     * @throws NotFoundException if the user does not exist
     */
    fun getUser(username: String): UserDTO =
        UserDTO(
            user = usersRepository
                .findByUsername(username)
                ?: throw NotFoundException("User with username $username not found")
        )
}
