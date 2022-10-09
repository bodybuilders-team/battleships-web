package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.controllers.games.models.ship.CoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class InputShotModelTests {
    @Test
    fun `InputShotModel creation is successful`() {
        InputShotModel(CoordinateModel('A', 1))
    }

    @Test
    fun `InputShotModel to InputShotDTO conversion is successful`() {
        val model = InputShotModel(CoordinateModel('A', 1))
        val dto = model.toInputShotDTO()

        assertEquals(model.coordinate.toCoordinateDTO(), dto.coordinate)
    }
}
