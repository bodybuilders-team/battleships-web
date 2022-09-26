package pt.isel.daw.battleships.database.model.ship

import pt.isel.daw.battleships.database.model.Coordinate
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
 * The Ship entity.
 *
 * @property id The ship's id.
 * @property type The ship's type.
 * @property coordinate The ship's coordinate.
 * @property orientation The ship's orientation.
 * @property lives The ship's lives.
 */
@Entity
@Table(name = "ships")
class Ship(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    val type: ShipType,

    @Embedded
    val coordinate: Coordinate,

    @Column(name = "orientation", nullable = false)
    @Enumerated(EnumType.STRING)
    val orientation: Orientation,

    @Column(name = "lives", nullable = false)
    val lives: Int
) {

    /**
     * Represents the possible orientations of a ship:
     * - HORIZONTAL: The ship is horizontal.
     * - VERTICAL: The ship is vertical.
     */
    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
