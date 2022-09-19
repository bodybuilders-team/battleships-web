package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "shots")
class Shot(
    @EmbeddedId
    val id: ShotPK,

    @Embedded
    val coordinate: Coordinate,

    @Column(name = "result", nullable = false)
    val result: String
) {
    // Needed for JPA
    constructor() : this(ShotPK(), Coordinate(), "")
}
