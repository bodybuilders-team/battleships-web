package pt.isel.daw.battleships.http.controllers.games.models.players.getOpponentShots

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModelTests.Companion.defaultFiredShotModel
import kotlin.test.Test

class GetOpponentShotsOutputModelTests {

    @Test
    fun `GetOpponentShotsOutputModel creation is successful`() {
        GetOpponentShotsOutputModel(
            shots = listOf(defaultFiredShotModel)
        )
    }

    companion object {
        val defaultGetOpponentShotsOutputModel
            get() = GetOpponentShotsOutputModel(
                shots = listOf(defaultFiredShotModel)
            )
    }
}
