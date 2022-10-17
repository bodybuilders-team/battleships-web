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
 * @property id the ship's id
 * @property type the ship's type
 * @property coordinate the ship's coordinate
 * @property orientation the ship's orientation
 * @property lives the ship's lives
 * @property coordinates the ship's coordinates
 * @property isSunk true if the ship is sunk, false otherwise
 */
@Entity
@Table(name = "ships")
class Ship(
    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    val type: ShipType,

    @Embedded
    val coordinate: Coordinate,

    @Column(name = "orientation", nullable = false)
    @Enumerated(EnumType.STRING)
    val orientation: Orientation,

    @Column(name = "lives", nullable = false)
    var lives: Int
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    val coordinates
        get() = (0 until lives).map {
            when (orientation) {
                Orientation.HORIZONTAL -> Coordinate(coordinate.col + it, coordinate.row)
                Orientation.VERTICAL -> Coordinate(coordinate.col, coordinate.row + it)
            }
        }

    val isSunk
        get() = lives == 0

    /**
     * Represents the possible orientations of a ship:
     * - HORIZONTAL: The ship is horizontal.
     * - VERTICAL: The ship is vertical.
     */
    enum class Orientation {
        HORIZONTAL,
        VERTICAL;
    }
}
