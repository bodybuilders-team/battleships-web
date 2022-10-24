package pt.isel.daw.battleships.domain

import pt.isel.daw.battleships.domain.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.domain.game.GameTests.Companion.defaultGame
import kotlin.test.Test

class PlayerTests {

    @Test
    fun `Player creation is successful`() {
        Player(
            game = defaultGame,
            user = defaultUser,
            points = 0
        )
    }

    companion object {
        val defaultPlayer
            get() = Player(
                game = defaultGame,
                user = defaultUser,
                points = 0
            )
    }
}