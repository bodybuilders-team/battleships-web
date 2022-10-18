package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.ship.InputShipDTO
import kotlin.test.Test

class InputShipDTOTests {

    @Test
    fun `InputShipDTO creation is successful`() {
        InputShipDTO(
            type = "type",
            coordinate = CoordinateDTO(col = 'A', row = 1),
            orientation = "HORIZONTAL"
        )
    }
}
