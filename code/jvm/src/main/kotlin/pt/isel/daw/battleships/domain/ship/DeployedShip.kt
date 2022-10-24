package pt.isel.daw.battleships.domain.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.exceptions.InvalidDeployedShipException
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
 * The DeployedShip entity.
 *
 * @property id the ship's id
 * @property player the player that owns the ship
 * @property type the ship's type
 * @property coordinate the ship's coordinate
 * @property orientation the ship's orientation
 * @property lives the ship's lives
 * @property isSunk true if the ship is sunk, false otherwise
 */
@Entity
@Table(name = "ships")
class DeployedShip : Ship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    val player: Player

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    override val type: ShipType

    @Embedded
    override val coordinate: Coordinate

    @Column(name = "orientation", nullable = false)
    @Enumerated(EnumType.STRING)
    override val orientation: Orientation

    @Column(name = "lives", nullable = false)
    var lives: Int

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        player: Player,
        type: ShipType,
        coordinate: Coordinate,
        orientation: Orientation,
        lives: Int
    ) : super(type, coordinate, orientation) { // TODO: Check this

        if (lives !in 0..type.size) {
            throw InvalidDeployedShipException("Ship lives must be between 0 and ${type.size}")
        }

        this.player = player
        this.type = type
        this.coordinate = coordinate
        this.orientation = orientation
        this.lives = lives
    }

    val isSunk
        get() = lives == 0
}
