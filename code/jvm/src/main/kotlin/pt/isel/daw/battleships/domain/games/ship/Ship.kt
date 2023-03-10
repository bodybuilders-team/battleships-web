package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.games.Coordinate
import pt.isel.daw.battleships.domain.games.ship.Ship.Orientation.HORIZONTAL
import pt.isel.daw.battleships.domain.games.ship.Ship.Orientation.VERTICAL

/**
 * A Ship entity.
 * A ship can be undeployed or deployed.
 *
 * @property type the ship type
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 * @property coordinates the ship's coordinates
 */
abstract class Ship(
    open val type: ShipType? = null, // Defaults needed for JPA
    open val coordinate: Coordinate? = null,
    open val orientation: Orientation? = null
) {

    val coordinates
        get() = getCoordinates(
            coordinate = coordinate ?: throw IllegalStateException("Ship has no coordinate"),
            size = type?.size ?: throw IllegalStateException("Ship has no type"),
            orientation = orientation ?: throw IllegalStateException("Ship has no orientation")
        )

    /**
     * Checks if this ship is overlapping with another ship.
     *
     * @param otherShip the other ship
     * @return true if the ships are overlapping, false otherwise
     */
    fun isOverlapping(otherShip: Ship): Boolean = otherShip.coordinates.any { it in coordinates }

    /**
     * The possible orientations of a ship.
     *
     * @property HORIZONTAL: the ship is horizontal
     * @property VERTICAL: the ship is vertical
     */
    enum class Orientation {
        HORIZONTAL,
        VERTICAL;
    }

    companion object {

        /**
         * Calculates the ship's coordinates.
         *
         * @property coordinate the coordinate of the ship
         * @property size the size of the ship
         * @property orientation the orientation of the ship
         *
         * @return the ship's coordinates
         */
        private fun getCoordinates(
            coordinate: Coordinate,
            size: Int,
            orientation: Orientation
        ): List<Coordinate> = (0 until size).map {
            when (orientation) {
                HORIZONTAL -> Coordinate(coordinate.col + it, coordinate.row)
                VERTICAL -> Coordinate(coordinate.col, coordinate.row + it)
            }
        }
    }
}
