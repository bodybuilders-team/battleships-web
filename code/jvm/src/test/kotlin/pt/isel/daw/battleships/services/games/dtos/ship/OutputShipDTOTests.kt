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
        OutputShipDTO("type", CoordinateDTO('A', 1), 'H', 3)
    }

    @Test
    fun `OutputShipDTO from Ship conversion is successful`() {
        val ship = Ship(
            ShipType("type", 3, 1, 10),
            Coordinate('A', 1), Ship.Orientation.HORIZONTAL, 3
        )
        val outputShipDTO = OutputShipDTO(ship)

        assertEquals(ship.type.shipName, outputShipDTO.type)
        assertEquals(CoordinateDTO(ship.coordinate), outputShipDTO.coordinate)
        assertEquals('H', outputShipDTO.orientation)
        assertEquals(ship.lives, outputShipDTO.lives)
    }
}