package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.domain.ship.Ship
import pt.isel.daw.battleships.domain.ship.ShipType
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputDeployedShipDTOTests {

    @Test
    fun `OutputShipDTO creation is successful`() {
        DeployedShipDTO(
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
            orientation = Ship.Orientation.HORIZONTAL,
            lives = 3
        )
        val deployedShipDTO = DeployedShipDTO(deployedShip)

        assertEquals(deployedShip.type.shipName, deployedShipDTO.type)
        assertEquals(CoordinateDTO(deployedShip.coordinate), deployedShipDTO.coordinate)
        assertEquals("HORIZONTAL", deployedShipDTO.orientation)
        assertEquals(deployedShip.lives, deployedShipDTO.lives)
    }
}
