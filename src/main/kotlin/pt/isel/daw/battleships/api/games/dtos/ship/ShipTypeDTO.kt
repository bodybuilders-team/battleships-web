package pt.isel.daw.battleships.api.games.dtos.ship

/**
 * Represents a ship type.
 *
 * @property name The name of the ship type.
 * @property size The size of the ship type.
 * @property points The points of the ship type.
 */
data class ShipTypeDTO(
    val name: String,
    val size: Int,
    val points: Int
)
