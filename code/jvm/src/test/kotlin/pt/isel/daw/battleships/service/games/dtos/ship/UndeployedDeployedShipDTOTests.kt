package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test

class UndeployedDeployedShipDTOTests {

    @Test
    fun `UndeployedShipDTO creation is successful`() {
        UndeployedShipDTO(
            type = "type",
            coordinate = CoordinateDTO(col = 'A', row = 1),
            orientation = "HORIZONTAL"
        )
    }
}
