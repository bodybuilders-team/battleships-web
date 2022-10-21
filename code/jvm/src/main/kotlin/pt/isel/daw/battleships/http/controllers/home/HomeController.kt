package pt.isel.daw.battleships.http.controllers.home

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.Uris
import pt.isel.daw.battleships.http.controllers.home.models.AuthorModel
import pt.isel.daw.battleships.http.controllers.home.models.HomeOutputModel
import pt.isel.daw.battleships.http.controllers.home.models.RepositoryModel
import pt.isel.daw.battleships.http.siren.Action
import pt.isel.daw.battleships.http.siren.Link
import pt.isel.daw.battleships.http.siren.SirenEntity
import pt.isel.daw.battleships.http.siren.SirenEntity.Companion.SIREN_TYPE

/**
 * Controller that handles the requests related to the home.
 */
@RestController
@RequestMapping(Uris.HOME, produces = [SIREN_TYPE])
class HomeController {

    /**
     * Handles the request to get the home page.
     *
     * @return the response to the request with the home page
     */
    @GetMapping
    fun home(): SirenEntity<HomeOutputModel> {
        val authors = listOf(
            AuthorModel(
                name = "André Jesus",
                email = "andre.jesus.pilar@gmail.com",
                github = "https://github.com/andre-j3sus"
            ),
            AuthorModel(
                name = "André Páscoa",
                email = "andre@pascoa.org",
                github = "https://github.com/devandrepascoa"
            ),
            AuthorModel(
                name = "Nyckollas Brandão",
                email = "nyckbrandao1236@gmail.com",
                github = "https://github.com/nyckoka"
            )
        )

        val homeModel = HomeOutputModel(
            title = "Battleships",
            version = "0.0.1",
            description = "Battleships is a game where you have to sink all the ships of your opponent.",
            authors = authors,
            repository = RepositoryModel(
                type = "git",
                url = "https://github.com/isel-leic-daw/2022-daw-leic51d-g03"
            )
        )

        return SirenEntity(
            `class` = listOf("home"),
            properties = homeModel,
            links = listOf(
                Link(
                    rel = listOf("self", "home"),
                    href = Uris.home()
                )
            ),
            actions = listOf(
                Action(
                    name = "list-users",
                    title = "List Users",
                    method = "GET",
                    href = Uris.users()
                ),
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
                ),
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
            )
        )
    }
}
