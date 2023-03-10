package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.test.Test

class UndeployedShipTests {

    @Test
    fun `UndeployedShip creation is successful`() {
        UndeployedShip(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Ship.Orientation.VERTICAL
        )
    }

    companion object {
        val defaultUndeployedShip
            get() = UndeployedShip(
                type = defaultShipType,
                coordinate = defaultCoordinate,
                orientation = Ship.Orientation.VERTICAL
            )
    }
}
