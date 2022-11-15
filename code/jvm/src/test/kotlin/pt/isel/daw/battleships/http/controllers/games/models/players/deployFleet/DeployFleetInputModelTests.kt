package pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet

import pt.isel.daw.battleships.http.controllers.games.models.players.ship.UndeployedShipModelTests.Companion.defaultUndeployedShipModel
import kotlin.test.Test

class DeployFleetInputModelTests {

    @Test
    fun `DeployFleetInputModel creation is successful`() {
        DeployFleetInputModel(fleet = listOf(defaultUndeployedShipModel))
    }

    companion object {
        val defaultDeployFleetInputModel
            get() = DeployFleetInputModel(fleet = listOf(defaultUndeployedShipModel))
    }
}
