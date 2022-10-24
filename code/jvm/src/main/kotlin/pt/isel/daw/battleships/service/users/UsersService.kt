package pt.isel.daw.battleships.service.users

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

/**
 * Service that handles the business logic of the users.
 */
interface UsersService {

    /**
     * Gets all users.
     *
     * @param offset the offset of the pagination
     * @param limit the limit of the pagination
     * @param orderBy the order by of the pagination
     * @param ascending if the users should be ordered by points in ascending order
     *
     * @return the DTO with the information of the users
     * @throws InvalidPaginationParamsException if the offset or limit are invalid
     */
    fun getUsers(offset: Int, limit: Int, orderBy: UsersOrder, ascending: Boolean): UsersDTO

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
     * @throws NotFoundException if the refresh token does not exist or if it is expired
     * @throws AuthenticationException if the refresh token is invalid
     */
    fun logout(refreshToken: String)

    /**
     * Refreshes the JWT token of a user.
     *
     * @param refreshToken the refresh token of the user
     *
     * @return the new JWT token for the user
     * @throws NotFoundException if the refresh token does not exist or if the refresh token is expired
     * @throws AuthenticationException if the refresh token is invalid
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