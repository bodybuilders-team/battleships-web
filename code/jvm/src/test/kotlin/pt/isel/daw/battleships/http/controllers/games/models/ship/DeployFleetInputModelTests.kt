package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test

class DeployFleetInputModelTests {

    @Test
    fun `DeployFleetInputModel creation is successful`() {
        DeployFleetInputModel(ships = listOf(InputShipModel("CARRIER", CoordinateModel('A', 1), "HORIZONTAL")))
    }
}
