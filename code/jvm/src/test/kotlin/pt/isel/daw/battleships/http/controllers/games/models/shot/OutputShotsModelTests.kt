package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test

class OutputShotsModelTests {

    @Test
    fun `OutputShotsModel creation is successful`() {
        OutputShotsModel(
            shots = listOf(OutputShotModel(CoordinateModel('A', 1), 1, ShotResultModel("HIT")))
        )
    }
}
