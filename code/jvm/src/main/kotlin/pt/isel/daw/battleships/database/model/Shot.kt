package pt.isel.daw.battleships.database.model

import pt.isel.daw.battleships.database.model.Shot.ShotResult.HIT
import pt.isel.daw.battleships.database.model.Shot.ShotResult.MISS
import pt.isel.daw.battleships.database.model.Shot.ShotResult.SUNK
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
    @Column(name = "round", nullable = false)
    val round: Int,

    @Embedded
    val coordinate: Coordinate,

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    val result: ShotResult
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    /**
     * Represents the possible results of a shot.
     *
     * @property HIT the shot hit a ship
     * @property MISS the shot missed and hit the water
     * @property SUNK the shot sunk a ship
     */
    enum class ShotResult {
        HIT,
        MISS,
        SUNK
    }
}
