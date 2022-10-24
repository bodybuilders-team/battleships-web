package pt.isel.daw.battleships.domain.game

import kotlin.test.Test

class GameConfigTests {

    @Test
    fun `GameConfig creation is successful`() {
        GameConfig(10, 60, 60, 4, listOf())
    }

    companion object {
        val defaultGameConfig
            get() = GameConfig(10, 60, 60, 4, listOf())
    }
}