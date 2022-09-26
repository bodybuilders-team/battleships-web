package pt.isel.daw.battleships.api.games.dtos.ship

import pt.isel.daw.battleships.api.games.dtos.CoordinateDTO
import javax.validation.constraints.Pattern

/**
 * Represents a ship.
 *
 * @property type The type of the ship.
 * @property state The state of the ship.
 * @property coordinate The coordinate of the ship.
 * @property orientation The orientation of the ship.
 */
data class ShipDTO(
    val type: String,
    val state: String,
    val coordinate: CoordinateDTO?,

    @Pattern(regexp = "[HV]")
    val orientation: Char?
)
