package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

/**
 * The Shot entity.
 *
 * @property id The id of the shot.
 * @property coordinate The coordinate of the shot.
 * @property result The result of the shot.
 */
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
) {
    /**
     * Represents the possible results of a shot:
     * - HIT: The shot hit a ship.
     * - MISS: The shot missed and hit the water.
     * - SUNK: The shot sunk a ship.
     */
    enum class ShotResult {
        HIT,
        MISS,
        SUNK
    }
}
