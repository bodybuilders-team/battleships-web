package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.games.Coordinate
import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun `isOverlapping returns true if the ship is overlapping all coordinates with another ship`() {
        val ship = object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.VERTICAL
        ) {}

        val ship2 = object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.VERTICAL
        ) {}

        assertTrue(ship.isOverlapping(ship2))
    }

    @Test
    fun `isOverlapping returns true if the ship is overlapping only one coordinate with another ship `() {
        val ship = object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.VERTICAL
        ) {}

        val ship2 = object : Ship(
            type = defaultShipType,
            coordinate = Coordinate('A', 5),
            orientation = Orientation.VERTICAL
        ) {}

        assertTrue(ship.isOverlapping(ship2))
    }

    @Test
    fun `isOverlapping returns false if the ship is not overlapping with another ship`() {
        val ship = object : Ship(
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Orientation.VERTICAL
        ) {}

        val ship2 = object : Ship(
            type = defaultShipType,
            coordinate = Coordinate('A', 6),
            orientation = Orientation.VERTICAL
        ) {}

        assertFalse(ship.isOverlapping(ship2))
    }
}
