package pt.isel.daw.battleships.domain.games

import pt.isel.daw.battleships.domain.exceptions.InvalidCoordinateException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class CoordinateTests {

    @Test
    fun `Coordinate creation is successful`() {
        val coordinate = Coordinate('A', 1)

        assertEquals('A', coordinate.col)
        assertEquals(1, coordinate.row)
    }

    @Test
    fun `Coordinate creation throws InvalidCoordinateException if col is below max cols range`() {
        assertFailsWith<InvalidCoordinateException> {
            Coordinate('A' - 1, 1)
        }
    }

    @Test
    fun `Coordinate creation throws InvalidCoordinateException if col is above max cols range`() {
        assertFailsWith<InvalidCoordinateException> {
            Coordinate('S', 1)
        }
    }

    @Test
    fun `Coordinate creation throws InvalidCoordinateException if row is below max rows range`() {
        assertFailsWith<InvalidCoordinateException> {
            Coordinate('A', 0)
        }
    }

    @Test
    fun `Coordinate creation throws InvalidCoordinateException if row is above max rows range`() {
        assertFailsWith<InvalidCoordinateException> {
            Coordinate('A', 19)
        }
    }

    @Test
    fun `equals returns true if coordinates are equal`() {
        val coordinate = Coordinate('A', 1)
        val coordinate2 = Coordinate('A', 1)

        assertEquals(coordinate, coordinate2)
    }

    @Test
    fun `equals returns false if coordinates are not equal`() {
        val coordinate = Coordinate('A', 1)
        val coordinate2 = Coordinate('A', 3)

        assertNotEquals(coordinate, coordinate2)
    }

    @Test
    fun `hashCode is the same for same coordinates`() {
        val coordinate = Coordinate('A', 1)
        val coordinate2 = Coordinate('A', 1)

        assertEquals(coordinate.hashCode(), coordinate2.hashCode())
    }

    @Test
    fun `hashCode is different for different coordinates`() {
        val coordinate = Coordinate('A', 1)
        val coordinate2 = Coordinate('A', 3)

        assertNotEquals(coordinate.hashCode(), coordinate2.hashCode())
    }

    companion object {
        val defaultCoordinate
            get() = Coordinate('A', 1)
    }
}
