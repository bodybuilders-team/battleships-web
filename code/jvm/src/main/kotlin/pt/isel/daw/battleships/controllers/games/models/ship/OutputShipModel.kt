package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.OutputShipDTO
import javax.validation.constraints.Pattern

/**
 * Represents a ship.
 *
 * @property type The type of the ship.
 * @property coordinate The coordinate of the ship.
 * @property orientation The orientation of the ship.
 * @property lives The number of lives of the ship.
 */
data class OutputShipModel(
    val type: String,
    val coordinate: CoordinateModel,

    @Pattern(regexp = "[HV]")
    val orientation: Char?,

    val lives: Int
) {
    constructor(ship: OutputShipDTO) : this(
        ship.type,
        CoordinateModel(ship.coordinate),
        ship.orientation,
        ship.lives
    )
}
