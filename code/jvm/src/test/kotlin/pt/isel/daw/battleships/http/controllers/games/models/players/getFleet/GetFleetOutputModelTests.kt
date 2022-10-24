package pt.isel.daw.battleships.http.controllers.games.models.players.getFleet

import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModelTests.Companion.defaultDeployedShipModel
import kotlin.test.Test

class GetFleetOutputModelTests {

    @Test
    fun `GetFleetOutputModel creation is successful`() {
        GetFleetOutputModel(
            ships = listOf(defaultDeployedShipModel)
        )
    }

    companion object {
        val defaultGetFleetOutputModel
            get() = GetFleetOutputModel(
                ships = listOf(defaultDeployedShipModel)
            )
    }
}
