package pt.isel.daw.battleships.http.controllers.games.models.games.joinGame

import kotlin.test.Test

class JoinGameOutputModelTests {

    @Test
    fun `JoinGameOutputModel creation is successful`() {
        JoinGameOutputModel(
            gameId = 1
        )
    }

    companion object {
        val defaultJoinGameOutputModel
            get() = JoinGameOutputModel(
                gameId = 1
            )
    }
}
