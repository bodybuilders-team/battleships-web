package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.exceptions.InvalidShipTypeException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ShipTypeTests {

    @Test
    fun `ShipType creation is successful`() {
        ShipType(
            shipName = "Carrier",
            size = 5,
            quantity = 1,
            points = 5
        )
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if ship name is shorter than minimum ship name length`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "",
                size = 5,
                quantity = 1,
                points = 5
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if ship name is longer than max ship name length`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "n".repeat(41),
                size = 5,
                quantity = 1,
                points = 5
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if min ship quantity is below valid range`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "Carrier",
                size = 5,
                quantity = -1,
                points = 5
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if min ship quantity is above valid range`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "Carrier",
                size = 5,
                quantity = 11,
                points = 5
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if min ship size is below valid range`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "Carrier",
                size = 0,
                quantity = 1,
                points = 5
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if min ship size is above valid range`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "Carrier",
                size = 8,
                quantity = 1,
                points = 5
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if min ship points is below valid range`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "Carrier",
                size = 5,
                quantity = 1,
                points = 0
            )
        }
    }

    @Test
    fun `ShipType creation throws InvalidShipTypeException if min ship points is above valid range`() {
        assertFailsWith<InvalidShipTypeException> {
            ShipType(
                shipName = "Carrier",
                size = 5,
                quantity = 1,
                points = 150
            )
        }
    }

    companion object {
        val defaultShipType
            get() = ShipType(
                shipName = "Carrier",
                size = 5,
                quantity = 1,
                points = 5
            )
    }
}
