package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.ship.OutputFleetDTO
import pt.isel.daw.battleships.dtos.games.ship.OutputShipDTO
import kotlin.test.Test

class OutputFleetDTOTests {

    @Test
    fun `OutputFleetDTO creation is successful`() {
        OutputFleetDTO(
            ships = listOf(
                OutputShipDTO(
                    type = "Aircraft Carrier",
                    coordinate = CoordinateDTO('A', 1),
                    orientation = "HORIZONTAL",
                    lives = 5
                )
            )
        )
    }
}