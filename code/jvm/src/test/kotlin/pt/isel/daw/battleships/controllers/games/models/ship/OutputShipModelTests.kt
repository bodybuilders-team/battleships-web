package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputShipDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputShipModelTests {
    @Test
    fun `OutputShipModel creation is successful`() {
        OutputShipModel("CARRIER", CoordinateModel('A', 1), 'H', 5)
    }

    @Test
    fun `OutputShipModel from OutputShipDTO conversion is successful`() {
        val outputShipDTO = OutputShipDTO("CARRIER", CoordinateDTO('A', 1), 'H', 5)
        val outputShipModel = OutputShipModel(outputShipDTO)

        assertEquals(outputShipDTO.type, outputShipModel.type)
        assertEquals(CoordinateModel(outputShipDTO.coordinate), outputShipModel.coordinate)
        assertEquals(outputShipDTO.orientation, outputShipModel.orientation)
        assertEquals(outputShipDTO.lives, outputShipModel.lives)
    }
}