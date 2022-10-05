package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO

/**
 * Represents a ship type model.
 *
 * @property shipName the name of the ship type
 * @property size the size of the ship type
 * @property quantity the quantity of ships of this type
 * @property points the points of the ship type
 */
data class ShipTypeModel(
    val shipName: String,
    val size: Int,
    val quantity: Int,
    val points: Int
) {
    constructor(shipType: ShipTypeDTO) : this(
        shipType.shipName,
        shipType.size,
        shipType.quantity,
        shipType.points
    )

    /**
     * Converts the ship type model to a DTO.
     *
     * @return the ship type DTO
     */
    fun toShipTypeDTO() = ShipTypeDTO(shipName, size, quantity, points)
}
