package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test

class CreateShotsInputModelTests {

    @Test
    fun `CreateShotsInputModel creation is successful`() {
        CreateShotsInputModel(
            shots = listOf(InputShotModel(CoordinateModel('A', 1)))
        )
    }
}
