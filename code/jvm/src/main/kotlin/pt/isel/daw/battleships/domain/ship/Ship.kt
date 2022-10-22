package pt.isel.daw.battleships.domain.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.ship.Ship.Orientation.HORIZONTAL
import pt.isel.daw.battleships.domain.ship.Ship.Orientation.VERTICAL

/**
 * Represents a Ship in the Battleships Game.
 * A ship can be undeployed or deployed.
 *
 * @property type the ship type
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 */
abstract class Ship(
    open val type: ShipType? = null,

    open val coordinate: Coordinate? = null,

    open val orientation: Orientation? = null
) {
    fun isOverlapping(ship: Ship): Boolean {

    }

    /**
     * Represents the possible orientations of a ship.
     *
     * @property HORIZONTAL: the ship is horizontal
     * @property VERTICAL: the ship is vertical
     */
    enum class Orientation {
        HORIZONTAL,
        VERTICAL;
    }
}
