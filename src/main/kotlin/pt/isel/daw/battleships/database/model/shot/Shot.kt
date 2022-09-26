package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

enum class ShotResult {
    HIT, MISS, SUNK
}

@Entity
@Table(name = "shots")
class Shot(
    @EmbeddedId
    val id: ShotId,

    @Embedded
    val coordinate: Coordinate,

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    val result: ShotResult
)
