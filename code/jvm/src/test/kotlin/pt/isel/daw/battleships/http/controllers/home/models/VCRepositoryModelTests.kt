package pt.isel.daw.battleships.http.controllers.home.models

import kotlin.test.Test

class VCRepositoryModelTests {

    @Test
    fun `RepositoryModel creation is successful`() {
        VCRepositoryModel(
            type = "git",
            url = "url"
        )
    }

    companion object {
        val defaultVCRepositoryModel
            get() = VCRepositoryModel(
                type = "git",
                url = "url"
            )
    }
}
