package pt.isel.daw.battleships.database.model

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
)
