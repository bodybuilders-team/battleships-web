package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.fireShots.FireShotsInputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.UnfiredShotModel
import kotlin.test.Test

class FireShotsInputModelTests {

    @Test
    fun `CreateShotsInputModel creation is successful`() {
        FireShotsInputModel(
            shots = listOf(UnfiredShotModel(CoordinateModel('A', 1)))
        )
    }
}
