package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.PlayerTests.Companion.defaultPlayer
import pt.isel.daw.battleships.domain.games.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.test.Test

class DeployedShipTests {

    @Test
    fun `DeployedShip creation is successful`() {
        DeployedShip(
            defaultPlayer,
            defaultShipType,
            defaultCoordinate,
            Ship.Orientation.VERTICAL,
            5
        )
    }

    companion object {
        val defaultDeployedShip
            get() = DeployedShip(
                defaultPlayer,
                defaultShipType,
                defaultCoordinate,
                Ship.Orientation.VERTICAL,
                5
            )
    }
}
