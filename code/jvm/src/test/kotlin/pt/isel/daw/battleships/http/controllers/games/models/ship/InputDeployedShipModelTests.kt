package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.ship.UndeployedShipModel
import kotlin.test.Test
import kotlin.test.assertEquals

class InputDeployedShipModelTests {

    @Test
    fun `InputShipModel creation is successful`() {
        UndeployedShipModel(
            type = "CARRIER",
            coordinate = CoordinateModel('A', 1),
            orientation = "HORIZONTAL"
        )
    }

    @Test
    fun `InputShipModel to InputShipDTO conversion is successful`() {
        val undeployedShipModel = UndeployedShipModel(
            type = "CARRIER",
            coordinate = CoordinateModel('A', 1),
            orientation = "HORIZONTAL"
        )
        val inputShipDTO = undeployedShipModel.toUndeployedShipDTO()

        assertEquals(undeployedShipModel.type, inputShipDTO.type)
        assertEquals(undeployedShipModel.coordinate.toCoordinateDTO(), inputShipDTO.coordinate)
        assertEquals(undeployedShipModel.orientation, inputShipDTO.orientation)
    }
}
