package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO

/**
 * Represents a ship type.
 *
 * @property shipName The name of the ship type.
 * @property size The size of the ship type.
 * @property points The points of the ship type.
 */
data class ShipTypeModel(
    val shipName: String,
    val size: Int,
    val quantity: Int,
    val points: Int
) {
    fun toShipTypeDTO() = ShipTypeDTO(shipName, size, quantity, points)

    constructor(shipType: ShipTypeDTO) : this(
        shipType.shipName,
        shipType.size,
        shipType.quantity,
        shipType.points
    )
}
