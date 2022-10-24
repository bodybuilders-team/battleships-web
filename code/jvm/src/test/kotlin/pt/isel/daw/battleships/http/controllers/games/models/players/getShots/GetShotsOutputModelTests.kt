package pt.isel.daw.battleships.http.controllers.games.models.players.getShots

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModelTests.Companion.defaultFiredShotModel
import kotlin.test.Test

class GetShotsOutputModelTests {

    @Test
    fun `GetShotsOutputModel creation is successful`() {
        GetShotsOutputModel(
            shots = listOf(defaultFiredShotModel)
        )
    }

    companion object {
        val defaultGetShotsOutputModel
            get() = GetShotsOutputModel(
                shots = listOf(defaultFiredShotModel)
            )
    }
}
