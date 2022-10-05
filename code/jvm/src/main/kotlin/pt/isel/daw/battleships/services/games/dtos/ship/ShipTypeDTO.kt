package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.database.model.ship.ShipType

/**
 * Represents a Ship Type DTO.
 *
 * @property shipName the name of the ship
 * @property size the size of the ship
 * @property quantity the quantity of ships of this type
 * @property points the points that the ship is worth
 */
data class ShipTypeDTO(
    val shipName: String,
    val size: Int,
    val quantity: Int,
    val points: Int
) {
    constructor(shipType: ShipType) : this(
        shipType.shipName,
        shipType.size,
        shipType.quantity,
        shipType.points
    )

    /**
     * Converts this DTO to a database model.
     *
     * @return the database model ship type
     */
    fun toShipType() = ShipType(shipName, size, quantity, points)
}
