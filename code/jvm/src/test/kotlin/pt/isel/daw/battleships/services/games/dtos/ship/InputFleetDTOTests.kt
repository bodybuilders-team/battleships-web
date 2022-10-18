package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.ship.InputFleetDTO
import pt.isel.daw.battleships.dtos.games.ship.InputShipDTO
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
