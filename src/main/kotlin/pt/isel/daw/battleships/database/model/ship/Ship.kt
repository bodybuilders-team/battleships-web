package pt.isel.daw.battleships.database.model.ship

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * The Ship entity.
 *
 * @property id The ship's id.
 * @property orientation The ship's orientation.
 * @property coordinate The ship's coordinate.
 * @property type The ship's type.
 * @property lives The ship's lives.
 */
@Entity
@Table(name = "ships")
class Ship(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "orientation", nullable = false)
    val orientation: String,

    @Embedded
    val coordinate: Coordinate,

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    val type: ShipType,

    @Column(name = "lives", nullable = false)
    val lives: Int
)
