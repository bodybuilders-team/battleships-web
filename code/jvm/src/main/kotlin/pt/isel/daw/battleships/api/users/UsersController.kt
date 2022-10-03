package pt.isel.daw.battleships.api.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.api.users.dtos.UserModel
import pt.isel.daw.battleships.api.users.dtos.createUser.CreateUserInputModel
import pt.isel.daw.battleships.api.users.dtos.createUser.CreateUserOutputModel
import pt.isel.daw.battleships.api.users.dtos.login.LoginUserInputModel
import pt.isel.daw.battleships.api.users.dtos.login.LoginUserOutputModel
import pt.isel.daw.battleships.services.users.UsersService

/**
 * Controller that handles the requests related to the users.
 *
 * @property usersService The service that handles the business logic related to the users.
 */
@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    /**
     * Handles the request to create a new user.
     *
     * @param userData The data of the user to be created.
     * @return the response to the request with the created user.
     */
    @PostMapping
    fun createUser(
        @RequestBody userData: CreateUserInputModel
    ): CreateUserOutputModel =
        CreateUserOutputModel(token = usersService.createUser(userData.toCreateUserRequest()))

    /**
     * Handles the request to log in a user.
     *
     * @param userData The data of the user to be logged in.
     * @return the response to the request with the token of the logged-in user.
     */
    @PostMapping("/login")
    fun login(
        @RequestBody userData: LoginUserInputModel
    ): LoginUserOutputModel =
        LoginUserOutputModel(
            token = usersService.login(
                userData.username,
                userData.password
            )
        )

    /**
     * Handles the request to get a user.
     *
     * @param username The username of the user.
     * @return the response to the request with the user.
     */
    @GetMapping("/{username}")
    fun getUser(
        @PathVariable username: String
    ): UserModel? =
        UserModel(usersService.getUser(username))
}
