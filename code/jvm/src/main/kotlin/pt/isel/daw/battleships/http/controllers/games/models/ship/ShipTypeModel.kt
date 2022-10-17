package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

/**
 * Represents a ship type model.
 *
 * @property shipName the name of the ship type
 * @property size the size of the ship type
 * @property quantity the quantity of ships of this type
 * @property points the points of the ship type
 */
data class ShipTypeModel(
    @Size(
        min = MIN_SHIP_NAME_LENGTH,
        max = MAX_SHIP_NAME_LENGTH,
        message = "Ship type name must be between $MIN_SHIP_NAME_LENGTH and $MAX_SHIP_NAME_LENGTH characters long"
    )
    val shipName: String,

    @Min(
        value = MIN_SHIP_QUANTITY.toLong(),
        message = "Ship type quantity must be greater or equal than $MIN_SHIP_QUANTITY"
    )
    @Max(
        value = MAX_SHIP_QUANTITY.toLong(),
        message = "Ship type quantity must be less or equal than $MAX_SHIP_QUANTITY"
    )
    val quantity: Int,

    @Min(value = MIN_SHIP_SIZE.toLong(), message = "Ship type size must be greater or equal than $MIN_SHIP_SIZE")
    @Max(value = MAX_SHIP_SIZE.toLong(), message = "Ship type size must be less or equal than $MAX_SHIP_SIZE")
    val size: Int,

    @Min(value = MIN_SHIP_POINTS.toLong(), message = "Ship type points must be greater or equal than $MIN_SHIP_POINTS")
    @Max(value = MAX_SHIP_POINTS.toLong(), message = "Ship type points must be less or equal than $MAX_SHIP_POINTS")
    val points: Int
) {
    constructor(shipType: ShipTypeDTO) : this(
        shipName = shipType.shipName,
        quantity = shipType.quantity,
        size = shipType.size,
        points = shipType.points
    )

    /**
     * Converts the ship type model to a DTO.
     *
     * @return the ship type DTO
     */
    fun toShipTypeDTO() = ShipTypeDTO(
        shipName = shipName,
        quantity = quantity,
        size = size,
        points = points
    )

    companion object {
        const val MIN_SHIP_NAME_LENGTH = 1
        const val MAX_SHIP_NAME_LENGTH = 40

        private const val MIN_SHIP_QUANTITY = 0
        private const val MAX_SHIP_QUANTITY = 10

        private const val MIN_SHIP_SIZE = 1
        private const val MAX_SHIP_SIZE = 7

        private const val MIN_SHIP_POINTS = 1
        private const val MAX_SHIP_POINTS = 100
    }
}
