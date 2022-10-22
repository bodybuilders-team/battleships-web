package pt.isel.daw.battleships.domain

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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate

        if (col != other.col) return false
        if (row != other.row) return false

        return true
    }
}
