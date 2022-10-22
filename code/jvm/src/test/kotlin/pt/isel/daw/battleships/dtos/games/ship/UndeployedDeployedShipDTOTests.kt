package pt.isel.daw.battleships.dtos.games.ship

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test

class UndeployedDeployedShipDTOTests {

    @Test
    fun `InputShipDTO creation is successful`() {
        InputShipDTO(
            type = "type",
            coordinate = CoordinateDTO(col = 'A', row = 1),
            orientation = "HORIZONTAL"
        )
    }
}
