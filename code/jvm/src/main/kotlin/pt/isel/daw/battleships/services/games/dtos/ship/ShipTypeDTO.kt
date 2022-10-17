package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.database.model.ship.ShipType

/**
 * Represents a Ship Type DTO.
 *
 * @property shipName the name of the ship
 * @property quantity the quantity of ships of this type
 * @property size the size of the ship
 * @property points the points that the ship is worth
 */
data class ShipTypeDTO(
    val shipName: String,
    val quantity: Int,
    val size: Int,
    val points: Int
) {
    constructor(shipType: ShipType) : this(
        shipName = shipType.shipName,
        quantity = shipType.quantity,
        size = shipType.size,
        points = shipType.points
    )

    /**
     * Converts this DTO to a database model.
     *
     * @return the database model ship type
     */
    fun toShipType() = ShipType(
        shipName = shipName,
        quantity = quantity,
        size = size,
        points = points
    )
}
