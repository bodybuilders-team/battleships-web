package pt.isel.daw.battleships.http.controllers.games.models.players.getOpponentFleet

import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModelTests.Companion.defaultDeployedShipModel
import kotlin.test.Test

class GetOpponentFleetOutputModelTests {

    @Test
    fun `GetOpponentFleetOutputModel creation is successful`() {
        GetOpponentFleetOutputModel(
            ships = listOf(defaultDeployedShipModel)
        )
    }

    companion object {
        val defaultGetOpponentFleetOutputModel
            get() = GetOpponentFleetOutputModel(
                ships = listOf(defaultDeployedShipModel)
            )
    }
}
