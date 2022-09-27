package pt.isel.daw.battleships.api.games.dtos.ship

import pt.isel.daw.battleships.database.model.ship.ShipType

/**
 * Represents a ship type.
 *
 * @property shipName The name of the ship type.
 * @property size The size of the ship type.
 * @property points The points of the ship type.
 */
data class ShipTypeDTO(
    val shipName: String,
    val size: Int,
    val points: Int
) {
    constructor(shipType: ShipType) : this(
        shipType.shipName,
        shipType.size,
        shipType.points
    )
}
