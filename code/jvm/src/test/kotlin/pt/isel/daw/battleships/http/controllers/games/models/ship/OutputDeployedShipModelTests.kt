package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.ship.OutputShipDTO
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputDeployedShipModelTests {

    @Test
    fun `OutputShipModel creation is successful`() {
        OutputShipModel(
            type = "CARRIER",
            coordinate = CoordinateModel('A', 1),
            orientation = "HORIZONTAL",
            lives = 5
        )
    }

    @Test
    fun `OutputShipModel from OutputShipDTO conversion is successful`() {
        val outputShipDTO = OutputShipDTO(
            type = "CARRIER",
            coordinate = CoordinateDTO('A', 1),
            orientation = "HORIZONTAL",
            lives = 5
        )
        val outputShipModel = OutputShipModel(outputShipDTO)

        assertEquals(outputShipDTO.type, outputShipModel.type)
        assertEquals(CoordinateModel(outputShipDTO.coordinate), outputShipModel.coordinate)
        assertEquals(outputShipDTO.orientation, outputShipModel.orientation)
        assertEquals(outputShipDTO.lives, outputShipModel.lives)
    }
}
