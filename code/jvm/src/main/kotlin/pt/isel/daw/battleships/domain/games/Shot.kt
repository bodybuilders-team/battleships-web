package pt.isel.daw.battleships.domain.games

import pt.isel.daw.battleships.domain.exceptions.InvalidShotException
import pt.isel.daw.battleships.domain.games.Shot.ShotResult.HIT
import pt.isel.daw.battleships.domain.games.Shot.ShotResult.MISS
import pt.isel.daw.battleships.domain.games.Shot.ShotResult.SUNK
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * The Shot entity.
 *
 * @property id the id of the shot
 * @property round the round of the shot
 * @property player the player that made the shot
 * @property coordinate the coordinate of the shot
 * @property result the result of the shot
 */
@Entity
@Table(name = "shots")
class Shot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    @Column(name = "round", nullable = false)
    val round: Int

    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    val player: Player

    @Embedded
    val coordinate: Coordinate

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    val result: ShotResult

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        round: Int,
        player: Player,
        coordinate: Coordinate,
        result: ShotResult
    ) {
        if (round < 1) throw InvalidShotException("Round must be greater than 0")

        this.round = round
        this.player = player
        this.coordinate = coordinate
        this.result = result
    }

    /**
     * The possible results of a shot.
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
