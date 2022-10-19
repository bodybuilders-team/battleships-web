package pt.isel.daw.battleships.services.users

import pt.isel.daw.battleships.dtos.users.UserDTO
import pt.isel.daw.battleships.dtos.users.UsersDTO
import pt.isel.daw.battleships.dtos.users.createUser.CreateUserInputDTO
import pt.isel.daw.battleships.dtos.users.createUser.CreateUserOutputDTO
import pt.isel.daw.battleships.dtos.users.login.LoginUserInputDTO
import pt.isel.daw.battleships.dtos.users.login.LoginUserOutputDTO
import pt.isel.daw.battleships.dtos.users.refresh.RefreshTokenOutputDTO
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.NotFoundException

/**
 * Service that handles the business logic of the users.
 */
interface UsersService {

    /**
     * Gets all users.
     *
     * @return the DTO with the information of all users
     */
    fun getUsers(offset: Int, limit: Int): UsersDTO

    /**
     * Creates a new user.
     *
     * @param createUserInputDTO the DTO with the data to create the user
     *
     * @return the JWT token for the new user
     * @throws AlreadyExistsException if the user already exists
     */
    fun createUser(createUserInputDTO: CreateUserInputDTO): CreateUserOutputDTO

    /**
     * Logs a user in.
     *
     * @param loginUserInputDTO the DTO with the data to log the user in
     *
     * @return the JWT token for the user
     * @throws NotFoundException if the user does not exist or the password is incorrect
     */
    fun login(loginUserInputDTO: LoginUserInputDTO): LoginUserOutputDTO

    fun logout(refreshToken: String)

    fun refreshToken(refreshToken: String): RefreshTokenOutputDTO

    /**
     * Gets the user with the given username.
     *
     * @param username the username of the user
     *
     * @return the DTO with the user's data
     * @throws NotFoundException if the user does not exist
     */
    fun getUser(username: String): UserDTO
}
