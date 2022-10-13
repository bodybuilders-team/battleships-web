package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.database.model.ship.ShipType
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipTypeDTOTests {
    @Test
    fun `ShipTypeDTO creation is successful`() {
        ShipTypeDTO("Carrier", 5, 1, 5)
    }

    @Test
    fun `ShipTypeDTO from ShipType conversion is successful`() {
        val shipType = ShipType("Carrier", 5, 1, 5)
        val shipTypeDTO = ShipTypeDTO(shipType)

        assertEquals(shipType.shipName, shipTypeDTO.shipName)
        assertEquals(shipType.size, shipTypeDTO.size)
        assertEquals(shipType.quantity, shipTypeDTO.quantity)
        assertEquals(shipType.points, shipTypeDTO.points)
    }

    @Test
    fun `ShipTypeDTO to ShipType conversion is successful`() {
        val shipTypeDTO = ShipTypeDTO("Carrier", 5, 1, 5)
        val shipType = shipTypeDTO.toShipType()

        assertEquals(shipTypeDTO.shipName, shipType.shipName)
        assertEquals(shipTypeDTO.size, shipType.size)
        assertEquals(shipTypeDTO.quantity, shipType.quantity)
        assertEquals(shipTypeDTO.points, shipType.points)
    }
}