package pt.isel.daw.battleships.dtos.games.ship

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import kotlin.test.Test

class InputFleetDTOTests {

    @Test
    fun `InputFleetDTO creation is successful`() {
        InputFleetDTO(
            ships = listOf(
                InputShipDTO(
                    type = "Aircraft Carrier",
                    coordinate = CoordinateDTO('A', 1),
                    orientation = "HORIZONTAL"
                )
            )
        )
    }
}
