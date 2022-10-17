package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipTypeModelTests {

    @Test
    fun `ShipTypeModel creation is successful`() {
        ShipTypeModel(
            shipName = "Cruiser",
            size = 1,
            quantity = 2,
            points = 3
        )
    }

    @Test
    fun `ShipTypeModel from ShipTypeDTO conversion is successful`() {
        val shipTypeDTO = ShipTypeDTO(
            shipName = "shipName",
            size = 1,
            quantity = 2,
            points = 3
        )
        val shipTypeModel = ShipTypeModel(shipTypeDTO)

        assertEquals(shipTypeDTO.shipName, shipTypeModel.shipName)
        assertEquals(shipTypeDTO.size, shipTypeModel.size)
        assertEquals(shipTypeDTO.quantity, shipTypeModel.quantity)
        assertEquals(shipTypeDTO.points, shipTypeModel.points)
    }

    @Test
    fun `ShipTypeModel to ShipTypeDTO conversion is successful`() {
        val shipTypeModel = ShipTypeModel(
            shipName = "shipName",
            size = 1,
            quantity = 2,
            points = 3
        )
        val shipTypeDTO = shipTypeModel.toShipTypeDTO()

        assertEquals(shipTypeModel.shipName, shipTypeDTO.shipName)
        assertEquals(shipTypeModel.size, shipTypeDTO.size)
        assertEquals(shipTypeModel.quantity, shipTypeDTO.quantity)
        assertEquals(shipTypeModel.points, shipTypeDTO.points)
    }
}
