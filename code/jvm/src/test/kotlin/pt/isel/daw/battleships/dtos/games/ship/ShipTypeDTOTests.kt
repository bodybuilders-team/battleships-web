package pt.isel.daw.battleships.dtos.games.ship

import pt.isel.daw.battleships.database.model.ship.ShipType
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipTypeDTOTests {

    @Test
    fun `ShipTypeDTO creation is successful`() {
        ShipTypeDTO(
            shipName = "Carrier",
            size = 5,
            quantity = 1,
            points = 5
        )
    }

    @Test
    fun `ShipTypeDTO from ShipType conversion is successful`() {
        val shipType = ShipType(
            shipName = "Carrier",
            size = 5,
            quantity = 1,
            points = 5
        )
        val shipTypeDTO = ShipTypeDTO(shipType)

        assertEquals(shipType.shipName, shipTypeDTO.shipName)
        assertEquals(shipType.size, shipTypeDTO.size)
        assertEquals(shipType.quantity, shipTypeDTO.quantity)
        assertEquals(shipType.points, shipTypeDTO.points)
    }

    @Test
    fun `ShipTypeDTO to ShipType conversion is successful`() {
        val shipTypeDTO = ShipTypeDTO(
            shipName = "Carrier",
            size = 5,
            quantity = 1,
            points = 5
        )
        val shipType = shipTypeDTO.toShipType()

        assertEquals(shipTypeDTO.shipName, shipType.shipName)
        assertEquals(shipTypeDTO.size, shipType.size)
        assertEquals(shipTypeDTO.quantity, shipType.quantity)
        assertEquals(shipTypeDTO.points, shipType.points)
    }
}
