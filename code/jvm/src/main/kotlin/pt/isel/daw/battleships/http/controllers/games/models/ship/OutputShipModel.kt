package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.dtos.games.ship.OutputShipDTO
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import javax.validation.constraints.Pattern

/**
 * Represents a ship.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 * @property lives the number of lives of the ship
 */
data class OutputShipModel(
    val type: String,
    val coordinate: CoordinateModel,

    @Pattern(regexp = ORIENTATION_REGEX, message = "Orientation must be either HORIZONTAL or VERTICAL")
    val orientation: String,

    val lives: Int
) {
    constructor(ship: OutputShipDTO) : this(
        type = ship.type,
        coordinate = CoordinateModel(ship.coordinate),
        orientation = ship.orientation,
        lives = ship.lives
    )

    companion object {
        const val ORIENTATION_REGEX = "HORIZONTAL|VERTICAL"
    }
}
