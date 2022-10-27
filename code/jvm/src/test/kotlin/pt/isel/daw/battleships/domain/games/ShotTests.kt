package pt.isel.daw.battleships.domain.games

import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.PlayerTests.Companion.defaultPlayer
import kotlin.test.Test

class ShotTests {

    @Test
    fun `Shot creation is successful`() {
        Shot(
            round = 1,
            player = defaultPlayer,
            coordinate = defaultCoordinate,
            result = Shot.ShotResult.HIT
        )
    }

    companion object {
        val defaultShot
            get() = Shot(
                round = 1,
                player = defaultPlayer,
                coordinate = defaultCoordinate,
                result = Shot.ShotResult.HIT
            )
    }
}
