package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.domain.games.ship.DeployedShipTests.Companion.defaultDeployedShip
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTOTests.Companion.defaultCoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class DeployedShipDTOTests {

    @Test
    fun `DeployedShipDTO creation is successful`() {
        DeployedShipDTO(
            type = "Aircraft Carrier",
            coordinate = defaultCoordinateDTO,
            orientation = "HORIZONTAL",
            lives = 3
        )
    }

    @Test
    fun `DeployedShipDTO from DeployedShip conversion is successful`() {
        val deployedShip = defaultDeployedShip

        val deployedShipDTO = DeployedShipDTO(deployedShip)

        assertEquals(deployedShip.type.shipName, deployedShipDTO.type)
        assertEquals(CoordinateDTO(deployedShip.coordinate), deployedShipDTO.coordinate)
        assertEquals("VERTICAL", deployedShipDTO.orientation)
        assertEquals(deployedShip.lives, deployedShipDTO.lives)
    }

    companion object {
        val defaultDeployedShipDTO
            get() = DeployedShipDTO(
                type = "Aircraft Carrier",
                coordinate = defaultCoordinateDTO,
                orientation = "HORIZONTAL",
                lives = 5
            )
    }
}
