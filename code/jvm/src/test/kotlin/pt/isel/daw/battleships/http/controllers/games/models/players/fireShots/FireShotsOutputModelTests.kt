package pt.isel.daw.battleships.http.controllers.games.models.players.fireShots

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModelTests.Companion.defaultFiredShotModel
import kotlin.test.Test

class FireShotsOutputModelTests {

    @Test
    fun `FireShotsOutputModel creation is successful`() {
        FireShotsOutputModel(
            shots = listOf(defaultFiredShotModel)
        )
    }

    companion object {
        val defaultFireShotsOutputModel
            get() = FireShotsOutputModel(
                shots = listOf(defaultFiredShotModel)
            )
    }
}