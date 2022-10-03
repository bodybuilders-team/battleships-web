package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.ship.ShipType
import pt.isel.daw.battleships.database.model.ship.ShipTypeId

data class ShipTypeDTO(
    val shipName: String,
    val size: Int,
    val quantity: Int,
    val points: Int
) {
    fun toShipType() = ShipType(ShipTypeId(shipName), size, quantity, points)

    constructor(shipType: ShipType) : this(
        shipType.shipTypeId.shipName,
        shipType.size,
        shipType.quantity,
        shipType.points
    )
}
