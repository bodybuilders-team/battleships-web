package pt.isel.daw.battleships.http.controllers.games.models.games.getGames

import kotlin.test.Test
import kotlin.test.assertEquals

class GetGamesOutputModelTests {

    @Test
    fun `GetGamesOutputModel creation is successful`() {
        val getGamesOutputModel = GetGamesOutputModel(totalCount = 1)
        assertEquals(1, getGamesOutputModel.totalCount)
    }

    companion object {
        val defaultGetGamesOutputModel
            get() = GetGamesOutputModel(totalCount = 1)
    }
}
