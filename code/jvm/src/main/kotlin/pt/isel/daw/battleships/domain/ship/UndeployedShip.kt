package pt.isel.daw.battleships.domain.ship

import pt.isel.daw.battleships.domain.Coordinate

/**
 * Represents a UndeployedShip.
 *
 * @property type the ship type
 * @property coordinate the coordinate of the ship
 * @property orientation the ship orientation
 */
data class UndeployedShip(
    override val type: ShipType,
    override val coordinate: Coordinate,
    override val orientation: Orientation
) : Ship(type, coordinate, orientation)