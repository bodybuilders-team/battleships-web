package pt.isel.daw.battleships.database.model

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Coordinate(
    @Column(name = "row", nullable = false)
    val row: Int,

    @Column(name = "col", nullable = false)
    val col: Char
) {
    // Needed for JPA
    constructor() : this(0, 'A')
}
