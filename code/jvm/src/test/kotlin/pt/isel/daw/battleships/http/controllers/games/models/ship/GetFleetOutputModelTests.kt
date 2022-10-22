package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.getFleet.GetFleetOutputModel
import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModel
import kotlin.test.Test

class GetFleetOutputModelTests {

    @Test
    fun `OutputShipsModel creation is successful`() {
        GetFleetOutputModel(
            ships = listOf(DeployedShipModel("CARRIER", CoordinateModel('A', 1), "HORIZONTAL", 5))
        )
    }
}
