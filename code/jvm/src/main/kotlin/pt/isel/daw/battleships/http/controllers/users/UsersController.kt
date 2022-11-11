package pt.isel.daw.battleships.http.controllers.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.Params
import pt.isel.daw.battleships.http.Uris
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.getUsers.GetUsersOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.login.LoginUserInputModel
import pt.isel.daw.battleships.http.controllers.users.models.login.LoginUserOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.logout.LogoutUserInputModel
import pt.isel.daw.battleships.http.controllers.users.models.refreshToken.RefreshTokenInputModel
import pt.isel.daw.battleships.http.controllers.users.models.refreshToken.RefreshTokenOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.register.RegisterUserInputModel
import pt.isel.daw.battleships.http.controllers.users.models.register.RegisterUserOutputModel
import pt.isel.daw.battleships.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.Action
import pt.isel.daw.battleships.http.media.siren.Link
import pt.isel.daw.battleships.http.media.siren.SirenEntity
import pt.isel.daw.battleships.http.media.siren.SirenEntity.Companion.SIREN_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SubEntity
import pt.isel.daw.battleships.http.media.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.service.users.UsersOrder
import pt.isel.daw.battleships.service.users.UsersService
import javax.validation.Valid

/**
 * Controller that handles the requests related to the users.
 *
 * @property usersService the service that handles the business logic related to the users
 */
@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE, PROBLEM_MEDIA_TYPE])
class UsersController(private val usersService: UsersService) {

    /**
     * Handles the request to get the user home page.
     *
     * @return the response to the request with the user home page
     */
    @GetMapping(Uris.USER_HOME)
    fun getUserHome() = SirenEntity<Unit>(
        `class` = listOf("user-home"),
        actions = listOf(
            Action(
                name = "logout",
                title = "Logout",
                method = "POST",
                href = Uris.usersLogout()
            ),
            Action(
                name = "refresh-token",
                title = "Refresh Token",
                method = "POST",
                href = Uris.usersRefreshToken()
            ),
            Action(
                name = "matchmake",
                title = "Matchmake",
                method = "POST",
                href = Uris.gamesMatchmake()
            ),
            Action(
                name = "list-games",
                title = "List Games",
                method = "GET",
                href = Uris.games()
            ),
            Action(
                name = "create-game",
                title = "Create Game",
                method = "POST",
                href = Uris.games()
            )
        ),
        links = listOf(
            Link(
                rel = listOf("self"),
                href = Uris.userHome()
            ),
            Link(
                rel = listOf("home"),
                href = Uris.home()
            )
        )
    )

    /**
     * Handles the request to get all the users.
     *
     * @param offset the offset of the users to be returned
     * @param limit the limit of the users to be returned
     * @param orderBy the order by of the users to be returned
     * @param sortDirectionStr if the users should be ordered by points in ascending order
     *
     * @return the response to the request with all the users
     */
    @GetMapping(Uris.USERS)
    fun getUsers(
        @RequestParam(Params.OFFSET_PARAM) offset: Int? = null,
        @RequestParam(Params.LIMIT_PARAM) limit: Int? = null,
        @RequestParam(Params.ORDER_BY_PARAM) orderBy: String? = null,
        @RequestParam(Params.SORT_DIRECTION_PARAM) sortDirectionStr: String? = null
    ): SirenEntity<GetUsersOutputModel> {
        val ascending = when (sortDirectionStr ?: Params.SORT_DIRECTION_ASCENDING) {
            Params.SORT_DIRECTION_ASCENDING -> true
            Params.SORT_DIRECTION_DESCENDING -> false
            else -> throw IllegalArgumentException(
                "Invalid sort order, must be ${Params.SORT_DIRECTION_ASCENDING} or ${Params.SORT_DIRECTION_DESCENDING}"
            )
        }

        val sortBy = if (orderBy != null) UsersOrder.valueOf(orderBy) else UsersOrder.POINTS

        val users = usersService.getUsers(
            offset = offset ?: Params.OFFSET_DEFAULT,
            limit = limit ?: Params.LIMIT_DEFAULT,
            orderBy = sortBy,
            ascending = ascending
        )

        return SirenEntity(
            `class` = listOf("users"),
            properties = GetUsersOutputModel(totalCount = users.totalCount),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.users()
                )
            ),
            entities = users.users.map { user ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf("item", "user-${user.username}"),
                    `class` = listOf("user"),
                    properties = GetUserOutputModel(user),
                    links = listOf(
                        Link(
                            rel = listOf("self"),
                            href = Uris.userByUsername(username = user.username)
                        )
                    )
                )
            }
        )
    }

    /**
     * Handles the request to register a new user.
     *
     * @param userData the data of the user to be created
     * @return the response to the request with the created user
     */
    @PostMapping(Uris.USERS)
    fun register(
        @Valid @RequestBody
        userData: RegisterUserInputModel
    ): SirenEntity<RegisterUserOutputModel> {
        val registerDTO = usersService.register(registerUserInputDTO = userData.toRegisterUserInputDTO())

        return SirenEntity(
            `class` = listOf("user"),
            properties = RegisterUserOutputModel(registerUserOutputDTO = registerDTO),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                ),
                Link(
                    rel = listOf("user-home"),
                    href = Uris.userHome()
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = Uris.userByUsername(username = userData.username)
                )
            ),
            actions = listOf(
                Action(
                    name = "refresh-token",
                    title = "Refresh Token",
                    method = "POST",
                    href = Uris.usersRefreshToken()
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
        @Valid @RequestBody
        userData: LoginUserInputModel
    ): SirenEntity<LoginUserOutputModel> {
        val loginDTO = usersService.login(loginUserInputDTO = userData.toLoginUserInputDTO())

        return SirenEntity(
            `class` = listOf("user"),
            properties = LoginUserOutputModel(loginUserOutputDTO = loginDTO),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                ),
                Link(
                    rel = listOf("user-home"),
                    href = Uris.userHome()
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = Uris.userByUsername(username = userData.username)
                )
            ),
            actions = listOf(
                Action(
                    name = "refresh-token",
                    title = "Refresh Token",
                    method = "POST",
                    href = Uris.usersRefreshToken()
                )
            )
        )
    }

    /**
     * Handles the request to log out a user.
     *
     * @param logoutUserInputModel the data of the user to be logged out
     * @return the response to the request
     */
    @PostMapping(Uris.USERS_LOGOUT)
    fun logout(
        @Valid @RequestBody
        logoutUserInputModel: LogoutUserInputModel
    ): SirenEntity<Unit> {
        usersService.logout(refreshToken = logoutUserInputModel.refreshToken)

        return SirenEntity(
            `class` = listOf("user"),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                )
            ),
            actions = listOf(
                Action(
                    name = "register",
                    title = "Register",
                    method = "POST",
                    href = Uris.users()
                ),
                Action(
                    name = "login",
                    title = "Login",
                    method = "POST",
                    href = Uris.usersLogin()
                )
            )
        )
    }

    /**
     * Handles the request to refresh the token of a user.
     *
     * @param refreshTokenInputModel the data of the user to be refreshed
     * @return the response to the request with the new token of the user
     */
    @PostMapping(Uris.USERS_REFRESH_TOKEN)
    fun refreshToken(
        @Valid @RequestBody
        refreshTokenInputModel: RefreshTokenInputModel
    ): SirenEntity<RefreshTokenOutputModel> {
        val refreshDTO = usersService.refreshToken(refreshToken = refreshTokenInputModel.refreshToken)

        return SirenEntity(
            `class` = listOf("user"),
            properties = RefreshTokenOutputModel(refreshTokenOutputDTO = refreshDTO),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
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
    ): SirenEntity<GetUserOutputModel> {
        val user = usersService.getUser(username = username)

        return SirenEntity(
            `class` = listOf("user"),
            properties = GetUserOutputModel(userDTO = user),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.userByUsername(username = username)
                ),
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                )
            )
        )
    }
}
