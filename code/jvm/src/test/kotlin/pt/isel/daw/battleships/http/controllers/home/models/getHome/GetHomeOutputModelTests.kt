package pt.isel.daw.battleships.http.controllers.home.models.getHome

import pt.isel.daw.battleships.http.controllers.home.models.VCRepositoryModel
import kotlin.test.Test

class GetHomeOutputModelTests {

    @Test
    fun `HomeOutputModel creation is successful`() {
        GetHomeOutputModel(
            title = "Battleships",
            version = "1.0.0",
            description = "Battleships is a game where you have to sink all the ships of your opponent.",
            repository = VCRepositoryModel(
                type = "git",
                url = "url"
            ),
            authors = emptyList()
        )
    }

    companion object {
        val defaultHomeOutputModel
            get() = GetHomeOutputModel(
                title = "Battleships",
                version = "1.0.0",
                description = "Battleships is a game where you have to sink all the ships of your opponent.",
                repository = VCRepositoryModel(
                    type = "git",
                    url = "url"
                ),
                authors = emptyList()
            )
    }
}
