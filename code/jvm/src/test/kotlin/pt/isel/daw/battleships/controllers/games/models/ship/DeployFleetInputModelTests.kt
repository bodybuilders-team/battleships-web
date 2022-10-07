package pt.isel.daw.battleships.controllers.games.models.ship

import kotlin.test.Test

class DeployFleetInputModelTests {
    @Test
    fun `DeployFleetInputModel creation is successful`() {
        DeployFleetInputModel(listOf(InputShipModel("CARRIER", CoordinateModel('A', 1), 'H')))
    }
}