package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.domain.game.GameConfigTests.Companion.defaultGameConfig
import pt.isel.daw.battleships.domain.game.GameStateTests.Companion.defaultGameState
import kotlin.test.Test

class GameTests {

    @Test
    fun `Game creation is successful`() {
        Game(
            "name",
            defaultUser,
            defaultGameConfig,
            defaultGameState
        )
    }

    companion object {
        val defaultGame
            get() = Game(
                "name",
                defaultUser,
                defaultGameConfig,
                defaultGameState
            )
    }
}
