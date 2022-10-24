package pt.isel.daw.battleships.http.controllers.games.models.players.fireShots

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.UnfiredShotModelTests.Companion.defaultUnfiredShotModel
import kotlin.test.Test

class FireShotsInputModelTests {

    @Test
    fun `FireShotsInputModel creation is successful`() {
        FireShotsInputModel(
            shots = listOf(defaultUnfiredShotModel)
        )
    }

    companion object {
        val defaultFireShotsInputModel
            get() = FireShotsInputModel(
                shots = listOf(defaultUnfiredShotModel)
            )
    }
}
