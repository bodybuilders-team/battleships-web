package pt.isel.daw.battleships.controllers.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.controllers.users.models.UserModel
import pt.isel.daw.battleships.controllers.users.models.createUser.CreateUserInputModel
import pt.isel.daw.battleships.controllers.users.models.createUser.CreateUserOutputModel
import pt.isel.daw.battleships.controllers.users.models.login.LoginUserInputModel
import pt.isel.daw.battleships.controllers.users.models.login.LoginUserOutputModel
import pt.isel.daw.battleships.controllers.utils.siren.EmbeddedLink
import pt.isel.daw.battleships.controllers.utils.siren.Link
import pt.isel.daw.battleships.controllers.utils.siren.SIREN_TYPE
import pt.isel.daw.battleships.controllers.utils.siren.SirenEntity
import pt.isel.daw.battleships.services.users.UsersService
import java.net.URI

/**
 * Controller that handles the requests related to the users.
 *
 * @property usersService the service that handles the business logic related to the users
 */
@RestController
@RequestMapping("/users", produces = [SIREN_TYPE])
class UsersController(private val usersService: UsersService) {

    /**
     * Handles the request to create a new user.
     *
     * @param userData the data of the user to be created
     * @return the response to the request with the created user
     */
    @PostMapping
    fun createUser(
        @RequestBody userData: CreateUserInputModel
    ): SirenEntity<CreateUserOutputModel> {
        val token = usersService.createUser(userData.toCreateUserRequestDTO())

        return SirenEntity(
            `class` = listOf("user"),
            properties = CreateUserOutputModel(token),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = URI("/home")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = URI("/users/${userData.username}")
                )
            )
        )
    }

    /**
     * Handles the request to log in a user.
     *
     * @param userData the data of the user to be logged in
     * @return the response to the request with the token of the logged-in user
     */
    @PostMapping("/login")
    fun login(
        @RequestBody userData: LoginUserInputModel
    ): SirenEntity<LoginUserOutputModel> {
        val token = usersService.login(userData.toLoginUserInputDTO())

        return SirenEntity(
            `class` = listOf("user"),
            properties = LoginUserOutputModel(token),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = URI("/home")
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = URI("/users/${userData.username}")
                )
            )
        )
    }

    /**
     * Handles the request to get a user.
     *
     * @param username the username of the user
     * @return the response to the request with the user
     */
    @GetMapping("/{username}")
    fun getUser(
        @PathVariable username: String
    ): SirenEntity<UserModel?> {
        val user = usersService.getUser(username)

        return SirenEntity(
            `class` = listOf("user"),
            properties = UserModel(user),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = URI("/users/$username")
                ),
                Link(
                    rel = listOf("home"),
                    href = URI("/home")
                )
            )
        )
    }
}
