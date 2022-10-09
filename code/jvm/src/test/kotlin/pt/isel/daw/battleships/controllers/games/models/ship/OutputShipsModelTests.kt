package pt.isel.daw.battleships.controllers.games.models.ship

import kotlin.test.Test

class OutputShipsModelTests {
    @Test
    fun `OutputShipsModel creation is successful`() {
        OutputShipsModel(
            listOf(OutputShipModel("CARRIER", CoordinateModel('A', 1), 'H', 5))
        )
    }
}
