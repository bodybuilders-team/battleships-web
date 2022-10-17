package pt.isel.daw.battleships.services.users

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.users.dtos.CreateUserRequestDTO
import pt.isel.daw.battleships.services.users.dtos.LoginUserInputDTO
import pt.isel.daw.battleships.services.users.dtos.UserDTO
import pt.isel.daw.battleships.services.users.dtos.UsersDTO
import pt.isel.daw.battleships.utils.JwtProvider
import pt.isel.daw.battleships.utils.JwtProvider.JwtPayload
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the users.
 *
 * @property usersRepository the repository of the users
 * @property passwordUtils the utils for password operations
 * @property jwtProvider the JWT provider
 */
@Service
@Transactional
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val passwordUtils: PasswordUtils,
    private val jwtProvider: JwtProvider
) : UsersService {

    override fun getUsers(): UsersDTO = usersRepository
        .findAll()
        .map { UserDTO(it) }
        .let { users ->
            UsersDTO(
                users = users,
                totalCount = users.size
            )
        }

    override fun createUser(createUserRequestDTO: CreateUserRequestDTO): String {
        if (usersRepository.existsByUsername(username = createUserRequestDTO.username)) {
            throw AlreadyExistsException("User with username ${createUserRequestDTO.username} already exists")
        }

        val user = User(
            username = createUserRequestDTO.username,
            email = createUserRequestDTO.email,
            hashedPassword = passwordUtils.hashPassword(
                username = createUserRequestDTO.username,
                password = createUserRequestDTO.password
            )
        )

        usersRepository.save(user)

        return jwtProvider.createToken(jwtPayload = JwtPayload(createUserRequestDTO.username))
    }

    override fun login(loginUserInputDTO: LoginUserInputDTO): String {
        val user = usersRepository
            .findByUsername(username = loginUserInputDTO.username)
            ?: throw NotFoundException("User with username ${loginUserInputDTO.username} not found")

        if (
            !passwordUtils.checkPassword(
                username = loginUserInputDTO.username,
                password = loginUserInputDTO.password,
                hashedPassword = user.hashedPassword
            )
        ) throw NotFoundException("Invalid username or password")

        return jwtProvider.createToken(JwtPayload(loginUserInputDTO.username))
    }

    override fun getUser(username: String): UserDTO =
        UserDTO(
            user = usersRepository
                .findByUsername(username)
                ?: throw NotFoundException("User with username $username not found")
        )
}
