package pt.isel.daw.battleships.domain.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.Player
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
class DeployedShip(
    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    val player: Player,

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    override val type: ShipType,

    @Embedded
    override val coordinate: Coordinate,

    @Column(name = "orientation", nullable = false)
    @Enumerated(EnumType.STRING)
    override val orientation: Orientation,

    @Column(name = "lives", nullable = false)
    var lives: Int
) : Ship(type, coordinate, orientation) {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    val isSunk
        get() = lives == 0
}
