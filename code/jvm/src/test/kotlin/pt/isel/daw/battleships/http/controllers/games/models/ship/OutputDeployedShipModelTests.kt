package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModel
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputDeployedShipModelTests {

    @Test
    fun `OutputShipModel creation is successful`() {
        DeployedShipModel(
            type = "CARRIER",
            coordinate = CoordinateModel('A', 1),
            orientation = "HORIZONTAL",
            lives = 5
        )
    }

    @Test
    fun `OutputShipModel from OutputShipDTO conversion is successful`() {
        val deployedShipDTO = DeployedShipDTO(
            type = "CARRIER",
            coordinate = CoordinateDTO('A', 1),
            orientation = "HORIZONTAL",
            lives = 5
        )
        val deployedShipModel = DeployedShipModel(deployedShipDTO)

        assertEquals(deployedShipDTO.type, deployedShipModel.type)
        assertEquals(CoordinateModel(deployedShipDTO.coordinate), deployedShipModel.coordinate)
        assertEquals(deployedShipDTO.orientation, deployedShipModel.orientation)
        assertEquals(deployedShipDTO.lives, deployedShipModel.lives)
    }
}
