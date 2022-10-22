package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test

class UndeployedFleetDTOTests {

    @Test
    fun `UndeployedFleetDTO creation is successful`() {
        UndeployedFleetDTO(
            ships = listOf(
                UndeployedShipDTO(
                    type = "Aircraft Carrier",
                    coordinate = CoordinateDTO('A', 1),
                    orientation = "HORIZONTAL"
                )
            )
        )
    }
}
