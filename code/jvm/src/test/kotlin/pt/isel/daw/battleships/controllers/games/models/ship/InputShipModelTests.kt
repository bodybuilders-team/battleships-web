package pt.isel.daw.battleships.controllers.games.models.ship

import kotlin.test.Test
import kotlin.test.assertEquals

class InputShipModelTests {
    @Test
    fun `InputShipModel creation is successful`() {
        InputShipModel("CARRIER", CoordinateModel('A', 1), 'H')
    }

    @Test
    fun `InputShipModel to InputShipDTO conversion is successful`() {
        val inputShipModel = InputShipModel("CARRIER", CoordinateModel('A', 1), 'H')
        val inputShipDTO = inputShipModel.toInputShipDTO()

        assertEquals(inputShipModel.type, inputShipDTO.type)
        assertEquals(inputShipModel.coordinate.toCoordinateDTO(), inputShipDTO.coordinate)
        assertEquals(inputShipModel.orientation, inputShipDTO.orientation)
    }
}
