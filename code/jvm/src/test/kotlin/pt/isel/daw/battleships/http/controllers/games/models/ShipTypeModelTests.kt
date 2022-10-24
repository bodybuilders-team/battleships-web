package pt.isel.daw.battleships.http.controllers.games.models

import pt.isel.daw.battleships.service.games.dtos.ship.ShipTypeDTOTests.Companion.defaultShipTypeDTO
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
        val shipTypeDTO = defaultShipTypeDTO

        val shipTypeModel = ShipTypeModel(shipTypeDTO)

        assertEquals(shipTypeDTO.shipName, shipTypeModel.shipName)
        assertEquals(shipTypeDTO.size, shipTypeModel.size)
        assertEquals(shipTypeDTO.quantity, shipTypeModel.quantity)
        assertEquals(shipTypeDTO.points, shipTypeModel.points)
    }

    @Test
    fun `ShipTypeModel to ShipTypeDTO conversion is successful`() {
        val shipTypeModel = defaultShipTypeModel

        val shipTypeDTO = shipTypeModel.toShipTypeDTO()

        assertEquals(shipTypeModel.shipName, shipTypeDTO.shipName)
        assertEquals(shipTypeModel.size, shipTypeDTO.size)
        assertEquals(shipTypeModel.quantity, shipTypeDTO.quantity)
        assertEquals(shipTypeModel.points, shipTypeDTO.points)
    }

    companion object {
        val defaultShipTypeModel = ShipTypeModel(
            shipName = "shipName",
            size = 1,
            quantity = 2,
            points = 3
        )
    }
}
