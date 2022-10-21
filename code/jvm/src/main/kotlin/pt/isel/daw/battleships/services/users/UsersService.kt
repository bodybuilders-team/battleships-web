package pt.isel.daw.battleships.services.users

import pt.isel.daw.battleships.dtos.users.UserDTO
import pt.isel.daw.battleships.dtos.users.UsersDTO
import pt.isel.daw.battleships.dtos.users.login.LoginUserInputDTO
import pt.isel.daw.battleships.dtos.users.login.LoginUserOutputDTO
import pt.isel.daw.battleships.dtos.users.refresh.RefreshTokenOutputDTO
import pt.isel.daw.battleships.dtos.users.register.RegisterUserInputDTO
import pt.isel.daw.battleships.dtos.users.register.RegisterUserOutputDTO
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.InvalidPaginationParams
import pt.isel.daw.battleships.services.exceptions.NotFoundException

/**
 * Service that handles the business logic of the users.
 */
interface UsersService {

    /**
     * Gets all users.
     *
     * @param offset the offset of the pagination
     * @param limit the limit of the pagination
     *
     * @return the DTO with the information of the users
     * @throws InvalidPaginationParams if the offset or limit are invalid
     */
    fun getUsers(offset: Int, limit: Int): UsersDTO

    /**
     * Registers a new user.
     *
     * @param registerUserInputDTO the DTO with the data to create the user
     *
     * @return the JWT token for the new user
     * @throws AlreadyExistsException if the user already exists
     */
    fun register(registerUserInputDTO: RegisterUserInputDTO): RegisterUserOutputDTO

    /**
     * Logs a user in.
     *
     * @param loginUserInputDTO the DTO with the data to log the user in
     *
     * @return the JWT token for the user
     * @throws NotFoundException if the user does not exist or if the password is incorrect
     */
    fun login(loginUserInputDTO: LoginUserInputDTO): LoginUserOutputDTO

    /**
     * Logs a user out.
     *
     * @param refreshToken the refresh token of the user
     *
     * @throws NotFoundException if the refresh token does not exist
     */
    fun logout(refreshToken: String)

    /**
     * Refreshes the JWT token of a user.
     *
     * @param refreshToken the refresh token of the user
     *
     * @return the new JWT token for the user
     * @throws NotFoundException if the refresh token does not exist or if the refresh token is expired
     */
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
