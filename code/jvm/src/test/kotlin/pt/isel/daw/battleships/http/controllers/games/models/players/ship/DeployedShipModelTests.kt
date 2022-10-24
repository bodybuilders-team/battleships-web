package pt.isel.daw.battleships.http.controllers.games.models.players.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModelTests.Companion.defaultCoordinateModel
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTOTests.Companion.defaultDeployedShipDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class DeployedShipModelTests {

    @Test
    fun `DeployedShipModel creation is successful`() {
        DeployedShipModel(
            type = "CARRIER",
            coordinate = defaultCoordinateModel,
            orientation = "HORIZONTAL",
            lives = 5
        )
    }

    @Test
    fun `DeployedShipModel from DeployedShipDTO conversion is successful`() {
        val deployedShipDTO = defaultDeployedShipDTO

        val deployedShipModel = DeployedShipModel(deployedShipDTO = deployedShipDTO)

        assertEquals(deployedShipDTO.type, deployedShipModel.type)
        assertEquals(CoordinateModel(deployedShipDTO.coordinate), deployedShipModel.coordinate)
        assertEquals(deployedShipDTO.orientation, deployedShipModel.orientation)
        assertEquals(deployedShipDTO.lives, deployedShipModel.lives)
    }

    companion object {
        val defaultDeployedShipModel
            get() = DeployedShipModel(
                type = "CARRIER",
                coordinate = defaultCoordinateModel,
                orientation = "HORIZONTAL",
                lives = 5
            )
    }
}
