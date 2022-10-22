package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet.DeployFleetInputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.ship.UndeployedShipModel
import kotlin.test.Test

class DeployFleetInputModelTests {

    @Test
    fun `DeployFleetInputModel creation is successful`() {
        DeployFleetInputModel(ships = listOf(UndeployedShipModel("CARRIER", CoordinateModel('A', 1), "HORIZONTAL")))
    }
}
