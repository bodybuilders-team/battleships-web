package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test

class OutputShipsModelTests {

    @Test
    fun `OutputShipsModel creation is successful`() {
        OutputShipsModel(
            ships = listOf(OutputShipModel("CARRIER", CoordinateModel('A', 1), "HORIZONTAL", 5))
        )
    }
}
