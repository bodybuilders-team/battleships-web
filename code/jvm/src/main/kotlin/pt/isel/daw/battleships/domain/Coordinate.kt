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
    fun toPoint(): Pair<Int, Int> = Pair(col - FIRST_COL, row - 1)
    fun fromPoint(x: Int, y: Int): Coordinate = Coordinate(
        col = FIRST_COL + x,
        row = y + 1
    )

    companion object {
        const val FIRST_COL = 'A'
    }
}
