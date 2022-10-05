package pt.isel.daw.battleships.database.model

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * The Shot entity.
 *
 * @property id the id of the shot
 * @property coordinate the coordinate of the shot
 * @property round the round of the shot
 * @property result the result of the shot
 */
@Entity
@Table(name = "shots")
class Shot(
    @Embedded
    val coordinate: Coordinate,

    @Column(name = "round", nullable = false)
    val round: Int,

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    val result: ShotResult
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

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
