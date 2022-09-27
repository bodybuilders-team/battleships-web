package pt.isel.daw.battleships.database.model

import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * Represents a coordinate in a board.
 *
 * @property row the row of the coordinate
 * @property col the column of the coordinate
 */
@Embeddable
class Coordinate(
    @Column(name = "row", nullable = false)
    val row: Int,

    @Column(name = "col", nullable = false)
    val col: Char
)
