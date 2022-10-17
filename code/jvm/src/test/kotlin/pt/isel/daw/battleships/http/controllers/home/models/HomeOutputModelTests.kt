package pt.isel.daw.battleships.http.controllers.home.models

import kotlin.test.Test

class HomeOutputModelTests {

    @Test
    fun `HomeOutputModel creation is successful`() {
        HomeOutputModel(
            title = "Battleships",
            version = "1.0.0",
            description = "Battleships is a game where you have to sink all the ships of your opponent.",
            repository = RepositoryModel(
                type = "git",
                url = "url"
            ),
            authors = emptyList()
        )
    }
}
