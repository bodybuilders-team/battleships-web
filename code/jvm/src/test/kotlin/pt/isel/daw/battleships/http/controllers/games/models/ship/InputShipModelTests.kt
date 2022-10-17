package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class InputShipModelTests {

    @Test
    fun `InputShipModel creation is successful`() {
        InputShipModel(
            type = "CARRIER",
            coordinate = CoordinateModel('A', 1),
            orientation = "HORIZONTAL"
        )
    }

    @Test
    fun `InputShipModel to InputShipDTO conversion is successful`() {
        val inputShipModel = InputShipModel(
            type = "CARRIER",
            coordinate = CoordinateModel('A', 1),
            orientation = "HORIZONTAL"
        )
        val inputShipDTO = inputShipModel.toInputShipDTO()

        assertEquals(inputShipModel.type, inputShipDTO.type)
        assertEquals(inputShipModel.coordinate.toCoordinateDTO(), inputShipDTO.coordinate)
        assertEquals(inputShipModel.orientation, inputShipDTO.orientation)
    }
}
