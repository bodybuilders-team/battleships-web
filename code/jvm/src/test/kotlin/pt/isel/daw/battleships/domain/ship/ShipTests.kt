package pt.isel.daw.battleships.domain.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipTests {

    @Test
    fun `Ship creation is successful`() {
        object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.HORIZONTAL
        ) {}
    }

    @Test
    fun `coordinates returns the correct coordinates if ship is horizontal`() {
        val ship = object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.HORIZONTAL
        ) {}

        assertEquals(
            listOf(
                Coordinate('A', 1),
                Coordinate('B', 1),
                Coordinate('C', 1),
                Coordinate('D', 1),
                Coordinate('E', 1)
            ),
            ship.coordinates
        )
    }

    @Test
    fun `coordinates returns the correct coordinates if ship is vertical`() {
        val ship = object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.VERTICAL
        ) {}

        assertEquals(
            listOf(
                Coordinate('A', 1),
                Coordinate('A', 2),
                Coordinate('A', 3),
                Coordinate('A', 4),
                Coordinate('A', 5)
            ),
            ship.coordinates
        )
    }
}