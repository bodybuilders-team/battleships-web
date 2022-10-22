package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.UnfiredShotModel
import kotlin.test.Test
import kotlin.test.assertEquals

class UnfiredShotModelTests {

    @Test
    fun `InputShotModel creation is successful`() {
        UnfiredShotModel(coordinate = CoordinateModel('A', 1))
    }

    @Test
    fun `InputShotModel to InputShotDTO conversion is successful`() {
        val model = UnfiredShotModel(coordinate = CoordinateModel('A', 1))
        val dto = model.toUnfiredShotDTO()

        assertEquals(model.coordinate.toCoordinateDTO(), dto.coordinate)
    }
}
