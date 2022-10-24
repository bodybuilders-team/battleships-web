package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.domain.ship.ShipTypeTests.Companion.defaultShipType
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
        val shipType = defaultShipType

        val shipTypeDTO = ShipTypeDTO(shipType)

        assertEquals(shipType.shipName, shipTypeDTO.shipName)
        assertEquals(shipType.size, shipTypeDTO.size)
        assertEquals(shipType.quantity, shipTypeDTO.quantity)
        assertEquals(shipType.points, shipTypeDTO.points)
    }

    @Test
    fun `ShipTypeDTO to ShipType conversion is successful`() {
        val shipTypeDTO = defaultShipTypeDTO

        val shipType = shipTypeDTO.toShipType()

        assertEquals(shipTypeDTO.shipName, shipType.shipName)
        assertEquals(shipTypeDTO.size, shipType.size)
        assertEquals(shipTypeDTO.quantity, shipType.quantity)
        assertEquals(shipTypeDTO.points, shipType.points)
    }

    companion object {
        val defaultShipTypeDTO
            get() = ShipTypeDTO(
                shipName = "Carrier",
                size = 5,
                quantity = 1,
                points = 5
            )
    }
}
