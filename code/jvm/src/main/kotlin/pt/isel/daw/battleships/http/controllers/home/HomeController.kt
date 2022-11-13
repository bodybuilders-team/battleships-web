package pt.isel.daw.battleships.http.controllers.home

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.http.controllers.home.models.AuthorModel
import pt.isel.daw.battleships.http.controllers.home.models.VCRepositoryModel
import pt.isel.daw.battleships.http.controllers.home.models.getHome.GetHomeOutputModel
import pt.isel.daw.battleships.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import pt.isel.daw.battleships.http.media.siren.SirenEntity
import pt.isel.daw.battleships.http.media.siren.SirenEntity.Companion.SIREN_MEDIA_TYPE
import pt.isel.daw.battleships.http.utils.Actions
import pt.isel.daw.battleships.http.utils.Links
import pt.isel.daw.battleships.http.utils.Rels
import pt.isel.daw.battleships.http.utils.Uris

/**
 * Controller that handles the requests related to the home.
 */
@RestController
@RequestMapping(Uris.HOME, produces = [SIREN_MEDIA_TYPE, PROBLEM_MEDIA_TYPE])
class HomeController {

    /**
     * Handles the request to get the home page.
     *
     * @return the response to the request with the home page
     */
    @GetMapping
    fun getHome(): SirenEntity<GetHomeOutputModel> =
        SirenEntity(
            `class` = listOf(Rels.HOME),
            properties = homeModel,
            links = listOf(
                Links.self(Uris.home())
            ),
            actions = listOf(
                Actions.listUsers,
                Actions.register,
                Actions.login
            )
        )

    companion object {
        private val authors = listOf(
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

        private val homeModel = GetHomeOutputModel(
            title = "Battleships",
            version = "0.1.0",
            description = "Battleships is a game where you have to sink all the ships of your opponent.",
            authors = authors,
            repository = VCRepositoryModel(
                type = "git",
                url = "https://github.com/isel-leic-daw/2022-daw-leic51d-g03"
            )
        )
    }
}
