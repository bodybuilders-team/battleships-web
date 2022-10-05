package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.*

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
