package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModelTests.Companion.defaultFiredShotModel
import kotlin.test.Test

class FiredShotsModelTests {

    @Test
    fun `FiredShotsModel creation is successful`() {
        FiredShotsModel(
            shots = listOf(defaultFiredShotModel)
        )
    }

    companion object {
        val defaultFiredShotsModel
            get() = FiredShotsModel(
                shots = listOf(defaultFiredShotModel)
            )
    }
}
