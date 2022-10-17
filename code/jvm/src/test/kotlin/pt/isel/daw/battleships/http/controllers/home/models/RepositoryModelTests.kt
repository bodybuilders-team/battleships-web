package pt.isel.daw.battleships.http.controllers.home.models

import kotlin.test.Test

class RepositoryModelTests {

    @Test
    fun `RepositoryModel creation is successful`() {
        RepositoryModel(
            type = "git",
            url = "url"
        )
    }
}
