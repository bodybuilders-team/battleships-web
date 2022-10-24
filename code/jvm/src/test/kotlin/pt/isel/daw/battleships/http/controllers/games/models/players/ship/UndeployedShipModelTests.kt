package pt.isel.daw.battleships.http.controllers.games.models.players.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModelTests.Companion.defaultCoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class UndeployedShipModelTests {

    @Test
    fun `UndeployedShipModel creation is successful`() {
        UndeployedShipModel(
            type = "CARRIER",
            coordinate = defaultCoordinateModel,
            orientation = "HORIZONTAL"
        )
    }

    @Test
    fun `UndeployedShipModel to UndeployedShipDTO conversion is successful`() {
        val undeployedShipModel = defaultUndeployedShipModel

        val undeployedShipDTO = undeployedShipModel.toUndeployedShipDTO()

        assertEquals(undeployedShipModel.type, undeployedShipDTO.type)
        assertEquals(undeployedShipModel.coordinate.toCoordinateDTO(), undeployedShipDTO.coordinate)
        assertEquals(undeployedShipModel.orientation, undeployedShipDTO.orientation)
    }

    companion object {
        val defaultUndeployedShipModel
            get() = UndeployedShipModel(
                type = "CARRIER",
                coordinate = defaultCoordinateModel,
                orientation = "HORIZONTAL"
            )
    }
}
