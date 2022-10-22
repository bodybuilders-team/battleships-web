package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test

class DeployedFleetDTOTests {

    @Test
    fun `OutputFleetDTO creation is successful`() {
        DeployedFleetDTO(
            ships = listOf(
                DeployedShipDTO(
                    type = "Aircraft Carrier",
                    coordinate = CoordinateDTO('A', 1),
                    orientation = "HORIZONTAL",
                    lives = 5
                )
            )
        )
    }
}
