package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTOTests.Companion.defaultDeployedShipDTO
import kotlin.test.Test

class DeployedFleetDTOTests {

    @Test
    fun `DeployedFleetDTO creation is successful`() {
        DeployedFleetDTO(
            ships = listOf(defaultDeployedShipDTO)
        )
    }

    companion object {
        val defaultDeployedFleetDTO
            get() = DeployedFleetDTO(
                ships = listOf(defaultDeployedShipDTO)
            )
    }
}
