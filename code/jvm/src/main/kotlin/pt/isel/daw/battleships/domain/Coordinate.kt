package pt.isel.daw.battleships.domain

import pt.isel.daw.battleships.domain.exceptions.InvalidCoordinate
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * The Coordinate entity.
 *
 * @property col the column of the coordinate
 * @property row the row of the coordinate
 */
@Embeddable
class Coordinate(
    @Column(name = "col", nullable = false)
    val col: Char,

    @Column(name = "row", nullable = false)
    val row: Int
) {
    init {
        if (col !in maxColsRange) {
            throw InvalidCoordinate(
                "Invalid Column"
            )
        }

        if (row !in maxRowsRange) {
            throw InvalidCoordinate(
                "Invalid Row"
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate

        if (col != other.col) return false
        if (row != other.row) return false

        return true
    }

    override fun hashCode(): Int {
        var result = col.hashCode()
        result = 31 * result + row
        return result
    }

    companion object {
        val maxColsRange = 'A' until 'R'
        val maxRowsRange = 1 until 18
    }
}
