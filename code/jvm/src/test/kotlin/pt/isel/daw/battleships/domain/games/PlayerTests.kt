package pt.isel.daw.battleships.domain.games

import pt.isel.daw.battleships.domain.games.game.GameTests.Companion.defaultGame
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
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
