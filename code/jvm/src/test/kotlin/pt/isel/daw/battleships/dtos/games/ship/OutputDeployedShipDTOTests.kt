package pt.isel.daw.battleships.dtos.games.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.domain.ship.ShipType
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputDeployedShipDTOTests {

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
        val deployedShip = DeployedShip(
            type = ShipType(shipName = "type", size = 3, quantity = 1, points = 10),
            coordinate = Coordinate(col = 'A', row = 1),
            orientation = DeployedShip.Orientation.HORIZONTAL,
            lives = 3
        )
        val outputShipDTO = OutputShipDTO(deployedShip)

        assertEquals(deployedShip.type.shipName, outputShipDTO.type)
        assertEquals(CoordinateDTO(deployedShip.coordinate), outputShipDTO.coordinate)
        assertEquals("HORIZONTAL", outputShipDTO.orientation)
        assertEquals(deployedShip.lives, outputShipDTO.lives)
    }
}
