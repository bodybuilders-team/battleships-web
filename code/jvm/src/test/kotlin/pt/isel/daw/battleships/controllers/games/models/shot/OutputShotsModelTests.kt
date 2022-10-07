package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.controllers.games.models.ship.CoordinateModel
import kotlin.test.Test

class OutputShotsModelTests {
    @Test
    fun `OutputShotsModel creation is successful`() {
        OutputShotsModel(
            listOf(OutputShotModel(CoordinateModel('A', 1), 1, ShotResultModel("HIT")))
        )
    }
}