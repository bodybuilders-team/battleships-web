package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotsModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.ShotResultModel
import kotlin.test.Test

class FiredShotsModelTests {

    @Test
    fun `OutputShotsModel creation is successful`() {
        FiredShotsModel(
            shots = listOf(FiredShotModel(CoordinateModel('A', 1), 1, ShotResultModel("HIT")))
        )
    }
}
