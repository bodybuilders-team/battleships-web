package pt.isel.daw.battleships.http.controllers.games.models.players.getBoard

import kotlin.test.Test

class GetBoardOutputModelTests {

    @Test
    fun `GetBoardOutputModel creation is successful`() {
        GetBoardOutputModel(
            board = listOf("A1", "B2")
        )
    }

    companion object {
        val defaultGetBoardOutputModel
            get() = GetBoardOutputModel(
                board = listOf("A1", "B2")
            )
    }
}
