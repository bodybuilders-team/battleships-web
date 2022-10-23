package pt.isel.daw.battleships.http.controllers.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
import pt.isel.daw.battleships.http.siren.Link
import pt.isel.daw.battleships.http.siren.SirenEntity
import pt.isel.daw.battleships.http.siren.SirenEntity.Companion.SIREN_TYPE
import pt.isel.daw.battleships.http.siren.SubEntity.EmbeddedLink
import pt.isel.daw.battleships.service.users.UsersService
import pt.isel.daw.battleships.service.utils.OffsetPageRequest.Companion.LIMIT_PARAM
import pt.isel.daw.battleships.service.utils.OffsetPageRequest.Companion.OFFSET_PARAM

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
     * @param offset the offset of the users to be returned
     * @param limit the limit of the users to be returned
     *
     * @return the response to the request with all the users
     */
    @GetMapping(Uris.USERS)
    fun getUsers(
        @RequestParam(OFFSET_PARAM) offset: Int,
        @RequestParam(LIMIT_PARAM) limit: Int
    ): SirenEntity<GetUsersOutputModel> {
        val users = usersService.getUsers(offset = offset, limit = limit)

        return SirenEntity(
            `class` = listOf("users"),
            properties = GetUsersOutputModel(totalCount = users.totalCount),
            links = listOf(
                Link(
                    rel = listOf("self"),
                    href = Uris.users()
                )
            ),
            entities = users.users.map {
                EmbeddedLink(
                    rel = listOf("item", "user-${it.username}"),
                    href = Uris.userByUsername(username = it.username),
                    title = it.username
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
        @RequestBody userData: RegisterUserInputModel
    ): SirenEntity<RegisterUserOutputModel> {
        val registerDTO = usersService.register(registerUserInputDTO = userData.toRegisterUserInputDTO())

        return SirenEntity(
            `class` = listOf("user"),
            properties = RegisterUserOutputModel(registerUserOutputDTO = registerDTO),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = Uris.userByUsername(username = userData.username)
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
        val loginDTO = usersService.login(loginUserInputDTO = userData.toLoginUserInputDTO())

        return SirenEntity(
            `class` = listOf("user"),
            properties = LoginUserOutputModel(loginUserOutputDTO = loginDTO),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
                )
            ),
            entities = listOf(
                EmbeddedLink(
                    rel = listOf("user"),
                    href = Uris.userByUsername(username = userData.username)
                )
            )
        )
    }

    // TODO:Should be authenticated?
    /**
     * Handles the request to log out a user.
     *
     * @param logoutUserInputModel the data of the user to be logged out
     * @return the response to the request
     */
    @PostMapping(Uris.USERS_LOGOUT)
    fun logout(
        @RequestBody logoutUserInputModel: LogoutUserInputModel
    ): SirenEntity<Unit> {
        usersService.logout(refreshToken = logoutUserInputModel.refreshToken)

        return SirenEntity(
            `class` = listOf("user"),
            links = listOf(
                Link(
                    rel = listOf("home"),
                    href = Uris.home()
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
        @RequestBody refreshTokenInputModel: RefreshTokenInputModel
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
