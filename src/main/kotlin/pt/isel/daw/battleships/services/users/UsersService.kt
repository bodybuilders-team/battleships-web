package pt.isel.daw.battleships.services.users

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.JwtUtils
import pt.isel.daw.battleships.JwtUtils.JwtPayload
import pt.isel.daw.battleships.Utils
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class UsersService(
    private val usersRepository: UsersRepository,
    private val utils: Utils,
    private val jwtUtils: JwtUtils
) {

    fun createUser(createUserRequest: CreateUserRequest): String {
        if (usersRepository.existsByUsername(createUserRequest.username)) {
            throw AlreadyExistsException("User with username ${createUserRequest.username} already exists")
        }

        val hashedPassword = utils.hashPassword(createUserRequest.password)

        val newUser = User(createUserRequest.username, hashedPassword)

        usersRepository.save(newUser)

        return jwtUtils.createToken(JwtPayload(createUserRequest.username))
    }

    fun login(username: String, password: String): String {
        val user = usersRepository.findByUsername(username)
            ?: throw NotFoundException("User with username $username not found")

        if (!utils.checkPassword(password, user.hashedPassword)) {
            throw NotFoundException("Invalid username or password")
        }

        return jwtUtils.createToken(JwtPayload(username))
    }

    fun getUser(username: String): UserResponse {
        return usersRepository.findByUsername(username)?.let {
            UserResponse(it)
        } ?: throw NotFoundException("User with username $username not found")
    }
}
