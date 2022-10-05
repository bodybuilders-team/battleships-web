package pt.isel.daw.battleships.database.model.ship

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.*

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

    /**
     * Represents the possible orientations of a ship:
     * - HORIZONTAL: The ship is horizontal.
     * - VERTICAL: The ship is vertical.
     */
    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    val coordinates
        get() = (0 until lives).map {
            when (orientation) {
                Orientation.HORIZONTAL -> Coordinate(coordinate.col + it, coordinate.row)
                Orientation.VERTICAL -> Coordinate(coordinate.col, coordinate.row + it)
            }
        }
}
