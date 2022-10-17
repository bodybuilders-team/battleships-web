package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.database.model.Coordinate
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.model.ship.ShipType
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputShipDTOTests {

    @Test
    fun `OutputShipDTO creation is successful`() {
        OutputShipDTO(
            type = "type",
            coordinate = CoordinateDTO('A', 1),
            orientation = "HORIZONTAL",
            lives = 3
        )
    }

    @Test
    fun `OutputShipDTO from Ship conversion is successful`() {
        val ship = Ship(
            type = ShipType(shipName = "type", size = 3, quantity = 1, points = 10),
            coordinate = Coordinate(col = 'A', row = 1),
            orientation = Ship.Orientation.HORIZONTAL,
            lives = 3
        )
        val outputShipDTO = OutputShipDTO(ship)

        assertEquals(ship.type.shipName, outputShipDTO.type)
        assertEquals(CoordinateDTO(ship.coordinate), outputShipDTO.coordinate)
        assertEquals("HORIZONTAL", outputShipDTO.orientation)
        assertEquals(ship.lives, outputShipDTO.lives)
    }
}
