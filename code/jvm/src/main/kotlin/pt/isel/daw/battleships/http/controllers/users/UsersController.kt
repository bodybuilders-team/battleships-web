package pt.isel.daw.battleships.http.controllers.users

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.controllers.users.models.getUser.GetUserOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.getUsers.GetUsersOutputModel
import pt.isel.daw.battleships.http.controllers.users.models.getUsers.GetUsersUserModel
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
import pt.isel.daw.battleships.http.pipeline.authentication.Authenticated
import pt.isel.daw.battleships.http.utils.Actions
import pt.isel.daw.battleships.http.utils.Links
import pt.isel.daw.battleships.http.utils.Params
import pt.isel.daw.battleships.http.utils.Rels
import pt.isel.daw.battleships.http.utils.Uris
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.users.UsersService
import pt.isel.daw.battleships.service.users.utils.UsersOrder
import pt.isel.daw.battleships.utils.JwtProvider
import javax.servlet.http.HttpServletResponse
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
            ascending = when (sortDirection ?: Params.SORT_DIR_DESCENDING) {
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
                    rel = listOf(Rels.ITEM, Rels.USER, "${Rels.USER}-${user.username}"),
                    properties = GetUsersUserModel(user),
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
        userData: LoginInputModel,
        response: HttpServletResponse
    ): SirenEntity<LoginOutputModel> {
        val loginDTO = usersService.login(loginInputDTO = userData.toLoginInputDTO())

        setAuthenticationCookies(response, loginDTO.accessToken, loginDTO.refreshToken)

        return SirenEntity(
            `class` = listOf(Rels.LOGIN),
            properties = LoginOutputModel(loginOutputDTO = loginDTO),
            links = listOf(
                Links.home,
                Links.userHome
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
    @Authenticated
    fun logout(
        @Valid @RequestBody(required = false)
        logoutUserInputModel: LogoutUserInputModel?,
        @RequestAttribute(JwtProvider.ACCESS_TOKEN_ATTRIBUTE) accessToken: String,
        @RequestAttribute(JwtProvider.REFRESH_TOKEN_ATTRIBUTE, required = false) refreshToken: String?,
        response: HttpServletResponse
    ): SirenEntity<Unit> {
        clearAuthenticationCookies(response)

        usersService.logout(
            accessToken = accessToken,
            refreshToken =
            // The refresh token may be received from cookies or from the request body
            (refreshToken ?: logoutUserInputModel?.refreshToken)
                ?: throw AuthenticationException("Refresh token is required")
        )

        return SirenEntity(
            `class` = listOf(Rels.LOGOUT),
            links = listOf(
                Links.home
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
    @Authenticated
    fun refreshToken(
        @Valid @RequestBody(required = false)
        refreshTokenInputModel: RefreshTokenInputModel?,
        @RequestAttribute(JwtProvider.ACCESS_TOKEN_ATTRIBUTE) accessToken: String,
        @RequestAttribute(JwtProvider.REFRESH_TOKEN_ATTRIBUTE, required = false) refreshToken: String?,
        response: HttpServletResponse
    ): SirenEntity<RefreshTokenOutputModel> {
        val refreshDTO = usersService.refreshToken(
            accessToken = accessToken,
            // The refresh token may be received from cookies or from the request body
            (refreshToken ?: refreshTokenInputModel?.refreshToken)
                ?: throw AuthenticationException("Refresh token is required")
        )

        setAuthenticationCookies(response, refreshDTO.accessToken, refreshDTO.refreshToken)

        return SirenEntity(
            `class` = listOf(Rels.REFRESH_TOKEN),
            properties = RefreshTokenOutputModel(refreshTokenOutputDTO = refreshDTO),
            links = listOf(
                Links.home,
                Links.userHome
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

    companion object {

        private fun setAuthenticationCookies(
            response: HttpServletResponse,
            accessToken: String,
            refreshToken: String
        ) {
            val accessTokenCookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(JwtProvider.accessTokenDuration)
                .sameSite("Strict")
                .build()

            val refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(JwtProvider.refreshTokenDuration)
                .sameSite("Strict")
                .build()

            response.addCookie(accessTokenCookie)
            response.addCookie(refreshTokenCookie)
        }

        private fun clearAuthenticationCookies(response: HttpServletResponse) {
            val accessTokenCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .maxAge(0)
                .sameSite("Strict")
                .build()

            val refreshTokenCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .maxAge(0)
                .sameSite("Strict")
                .build()

            response.addCookie(accessTokenCookie)
            response.addCookie(refreshTokenCookie)
        }

        private fun HttpServletResponse.addCookie(cookie: ResponseCookie) {
            this.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
        }
    }
}
