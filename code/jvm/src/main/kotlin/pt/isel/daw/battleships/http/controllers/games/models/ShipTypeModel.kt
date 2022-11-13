package pt.isel.daw.battleships.http.controllers.games.models

import pt.isel.daw.battleships.service.games.dtos.ship.ShipTypeDTO
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

/**
 * A Ship Type Model.
 *
 * @property shipName the name of the ship type
 * @property size the size of the ship type
 * @property quantity the quantity of ships of this type
 * @property points the points of the ship type
 */
data class ShipTypeModel(
    @field:Size(
        min = MIN_SHIP_NAME_LENGTH,
        max = MAX_SHIP_NAME_LENGTH,
        message = "Ship type name must be between $MIN_SHIP_NAME_LENGTH and $MAX_SHIP_NAME_LENGTH characters long"
    )
    val shipName: String,

    @field:Min(
        value = MIN_SHIP_QUANTITY.toLong(),
        message = "Ship type quantity must be greater or equal than $MIN_SHIP_QUANTITY"
    )
    @field:Max(
        value = MAX_SHIP_QUANTITY.toLong(),
        message = "Ship type quantity must be less or equal than $MAX_SHIP_QUANTITY"
    )
    val quantity: Int,

    @field:Min(value = MIN_SHIP_SIZE.toLong(), message = "Ship type size must be greater or equal than $MIN_SHIP_SIZE")
    @field:Max(value = MAX_SHIP_SIZE.toLong(), message = "Ship type size must be less or equal than $MAX_SHIP_SIZE")
    val size: Int,

    @field:Min(
        value = MIN_SHIP_POINTS.toLong(),
        message = "Ship type points must be greater or equal than $MIN_SHIP_POINTS"
    )
    @field:Max(
        value = MAX_SHIP_POINTS.toLong(),
        message = "Ship type points must be less or equal than $MAX_SHIP_POINTS"
    )
    val points: Int
) {
    constructor(shipTypeDTO: ShipTypeDTO) : this(
        shipName = shipTypeDTO.shipName,
        quantity = shipTypeDTO.quantity,
        size = shipTypeDTO.size,
        points = shipTypeDTO.points
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
