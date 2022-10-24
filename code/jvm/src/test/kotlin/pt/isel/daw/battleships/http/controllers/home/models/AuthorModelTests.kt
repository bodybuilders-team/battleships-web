package pt.isel.daw.battleships.http.controllers.home.models

import kotlin.test.Test

class AuthorModelTests {

    @Test
    fun `AuthorModel creation is successful`() {
        AuthorModel(
            name = "João",
            email = "joao@mail.com",
            github = "joao@github.com"
        )
    }

    companion object {
        val defaultAuthorModel
            get() = AuthorModel(
                name = "João",
                email = "joao@mail.com",
                github = "joao@github.com"
            )
    }
}
