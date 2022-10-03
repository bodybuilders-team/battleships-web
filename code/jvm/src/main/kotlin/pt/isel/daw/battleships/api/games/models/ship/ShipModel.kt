package pt.isel.daw.battleships.api.games.models.ship

import pt.isel.daw.battleships.api.games.models.CoordinateModel
import javax.validation.constraints.Pattern

/**
 * Represents a ship.
 *
 * @property type The type of the ship.
 * @property coordinate The coordinate of the ship.
 * @property orientation The orientation of the ship.
 * @property lives The number of lives of the ship.
 */
data class ShipModel(
    val type: String,
    val coordinate: CoordinateModel?,

    @Pattern(regexp = "[HV]")
    val orientation: Char?,

    val lives: Int
)
