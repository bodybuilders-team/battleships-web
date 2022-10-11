package pt.isel.daw.battleships.controllers.home

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.controllers.home.models.AuthorModel
import pt.isel.daw.battleships.controllers.home.models.HomeOutputModel
import pt.isel.daw.battleships.controllers.home.models.RepositoryModel
import pt.isel.daw.battleships.controllers.utils.LinkModel

/**
 * Controller that handles the requests related to the home.
 */
@RestController
@RequestMapping("/home")
class HomeController {

    /**
     * Handles the request to get the home page.
     *
     * @return the response to the request with the home page
     */
    @GetMapping
    fun home(): HomeOutputModel {
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

        return HomeOutputModel(
            title = "Battleships",
            version = "0.0.1",
            description = "Battleships is a game where you have to sink all the ships of your opponent.",
            authors = authors,
            repository = RepositoryModel(
                type = "git",
                url = "https://github.com/isel-leic-daw/2022-daw-leic51d-g03"
            ),
            links = listOf(
                LinkModel(
                    rel = "self",
                    href = "/home",
                    method = "GET",
                    requiresAuth = false
                ),
                LinkModel(
                    rel = "home",
                    href = "/",
                    method = "GET",
                    requiresAuth = false
                ),
                LinkModel(
                    rel = "register",
                    href = "/users",
                    method = "POST",
                    requiresAuth = false
                ),
                LinkModel(
                    rel = "login",
                    href = "/users/login",
                    method = "POST",
                    requiresAuth = false
                ),
                LinkModel(
                    rel = "games",
                    href = "/games",
                    method = "GET",
                    requiresAuth = true
                ),
                LinkModel(
                    rel = "matchmaking",
                    href = "/games/matchmaking",
                    method = "POST",
                    requiresAuth = true
                ) // TODO: Add join game, when LinKModel supports query parameters
            )
        )
    }
}
