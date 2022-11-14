package pt.isel.daw.battleships.http.controllers.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.getUsers.GetUsersOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.login.LoginInputModel
import pt.isel.daw.battleships.http.controllers.users.models.login.LoginOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.logout.LogoutUserInputModel
import pt.isel.daw.battleships.http.controllers.users.models.refreshToken.RefreshTokenInputModel
import pt.isel.daw.battleships.http.controllers.users.models.refreshToken.RefreshTokenOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.register.RegisterInputModel
import pt.isel.daw.battleships.http.controllers.users.models.register.RegisterOutputModel
import pt.isel.daw.battleships.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SirenEntity
import pt.isel.daw.battleships.http.media.siren.SirenEntity.Companion.SIREN_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SubEntity
import pt.isel.daw.battleships.http.media.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.http.utils.Actions
import pt.isel.daw.battleships.http.utils.Links
import pt.isel.daw.battleships.http.utils.Params
import pt.isel.daw.battleships.http.utils.Rels
import pt.isel.daw.battleships.http.utils.Uris
import pt.isel.daw.battleships.service.users.UsersService
import pt.isel.daw.battleships.service.users.utils.UsersOrder
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
        `class` = listOf(Rels.USER_HOME),
        actions = listOf(
            Actions.logout,
            Actions.refreshToken,
            Actions.listGames,
            Actions.createGame,
            Actions.matchmake
        ),
        links = listOf(
            Links.self(Uris.userHome()),
            Links.home
        )
    )

    /**
     * Handles the request to get all the users.
     *
     * @param offset the offset of the users to be returned
     * @param limit the limit of the users to be returned
     * @param orderBy the order by of the users to be returned
     * @param sortDirection if the users should be ordered by points in ascending order
     *
     * @return the response to the request with all the users
     */
    @GetMapping(Uris.USERS)
    fun getUsers(
        @RequestParam(Params.OFFSET_PARAM) offset: Int? = null,
        @RequestParam(Params.LIMIT_PARAM) limit: Int? = null,
        @RequestParam(Params.ORDER_BY_PARAM) orderBy: String? = null,
        @RequestParam(Params.SORT_DIRECTION_PARAM) sortDirection: String? = null
    ): SirenEntity<GetUsersOutputModel> {
        val usersDTO = usersService.getUsers(
            offset = offset ?: Params.OFFSET_DEFAULT,
            limit = limit ?: Params.LIMIT_DEFAULT,
            orderBy = if (orderBy != null) UsersOrder.valueOf(orderBy) else UsersOrder.POINTS,
            ascending = when (sortDirection ?: Params.SORT_DIR_ASCENDING) {
                Params.SORT_DIR_ASCENDING -> true
                Params.SORT_DIR_DESCENDING -> false
                else -> throw IllegalArgumentException(
                    "Invalid sort order, must be ${Params.SORT_DIR_ASCENDING} or ${Params.SORT_DIR_DESCENDING}"
                )
            }
        )

        return SirenEntity(
            `class` = listOf(Rels.LIST_USERS),
            properties = GetUsersOutputModel(totalCount = usersDTO.totalCount),
            links = listOf(
                Links.self(Uris.users()),
                Links.home
            ),
            entities = usersDTO.users.map { user ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM, "user-${user.username}"),
                    properties = GetUserOutputModel(user),
                    links = listOf(
                        Links.self(Uris.userByUsername(username = user.username)),
                        Links.home
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
        userData: RegisterInputModel
    ): SirenEntity<RegisterOutputModel> {
        val registerDTO = usersService.register(registerInputDTO = userData.toRegisterInputDTO())

        return SirenEntity(
            `class` = listOf(Rels.REGISTER),
            properties = RegisterOutputModel(registerOutputDTO = registerDTO),
            links = listOf(
                Links.home,
                Links.userHome
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf(Rels.USER),
                    href = Uris.userByUsername(username = userData.username)
                )
            ),
            actions = listOf(
                Actions.refreshToken,
                Actions.logout
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
        userData: LoginInputModel
    ): SirenEntity<LoginOutputModel> {
        val loginDTO = usersService.login(loginInputDTO = userData.toLoginInputDTO())

        return SirenEntity(
            `class` = listOf(Rels.LOGIN),
            properties = LoginOutputModel(loginOutputDTO = loginDTO),
            links = listOf(
                Links.home,
                Links.userHome
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf(Rels.USER),
                    href = Uris.userByUsername(username = userData.username)
                )
            ),
            actions = listOf(
                Actions.refreshToken,
                Actions.logout
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
            `class` = listOf(Rels.LOGOUT),
            links = listOf(
                Links.home
            ),
            actions = listOf(
                Actions.login,
                Actions.register
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
            `class` = listOf(Rels.REFRESH_TOKEN),
            properties = RefreshTokenOutputModel(refreshTokenOutputDTO = refreshDTO),
            links = listOf(
                Links.home
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
        val userDTO = usersService.getUser(username = username)

        return SirenEntity(
            `class` = listOf(Rels.USER),
            properties = GetUserOutputModel(userDTO = userDTO),
            links = listOf(
                Links.self(Uris.userByUsername(username = username)),
                Links.home
            )
        )
    }
}
