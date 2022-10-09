package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.controllers.games.models.ship.CoordinateModel
import kotlin.test.Test

class CreateShotsInputModelTests {
    @Test
    fun `CreateShotsInputModel creation is successful`() {
        CreateShotsInputModel(
            listOf(InputShotModel(CoordinateModel('A', 1)))
        )
    }
}
