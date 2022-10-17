package pt.isel.daw.battleships.http.controllers.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.Uris
import pt.isel.daw.battleships.http.controllers.users.models.UserModel
import pt.isel.daw.battleships.http.controllers.users.models.UsersOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.createUser.CreateUserInputModel
import pt.isel.daw.battleships.http.controllers.users.models.createUser.CreateUserOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.login.LoginUserInputModel
import pt.isel.daw.battleships.http.controllers.users.models.login.LoginUserOutputModel
import pt.isel.daw.battleships.http.siren.Link
import pt.isel.daw.battleships.http.siren.SirenEntity
import pt.isel.daw.battleships.http.siren.SirenEntity.Companion.SIREN_TYPE
import pt.isel.daw.battleships.http.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.services.users.UsersService

/**
 * Controller that handles the requests related to the users.
 *
 * @property usersService the service that handles the business logic related to the users
 */
@RestController
@RequestMapping(produces = [SIREN_TYPE])
class UsersController(private val usersService: UsersService) {

    /**
     * Handles the request to get all the users.
     *
     * @return the response to the request with all the users
     */
    @GetMapping(Uris.USERS)
    fun getUsers(): SirenEntity<UsersOutputModel> {
        val users = usersService.getUsers()

        return SirenEntity(
            `class` = listOf("users"),
            properties = UsersOutputModel(users.totalCount),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.users()
                )
            ),
            entities = users.users.map {
                EmbeddedLink(
                    rel = listOf("item", "user-${it.username}"),
                    href = Uris.userByUsername(it.username),
                    title = it.username
                )
            }
        )
    }

    /**
     * Handles the request to create a new user.
     *
     * @param userData the data of the user to be created
     * @return the response to the request with the created user
     */
    @PostMapping(Uris.USERS)
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
                    href = Uris.home()
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = Uris.userByUsername(userData.username)
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
    @PostMapping(Uris.USERS_LOGIN)
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
                    href = Uris.home()
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = Uris.userByUsername(userData.username)
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
    @GetMapping(Uris.USERS_GET_BY_USERNAME)
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
                    href = Uris.userByUsername(username)
                ),
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                )
            )
        )
    }
}
