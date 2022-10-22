package pt.isel.daw.battleships.http.controllers.games.models.players.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTO
import javax.validation.constraints.Pattern

/**
 * Represents a ship.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 * @property lives the number of lives of the ship
 */
data class DeployedShipModel(
    val type: String,
    val coordinate: CoordinateModel,

    @Pattern(regexp = ORIENTATION_REGEX, message = "Orientation must be either HORIZONTAL or VERTICAL")
    val orientation: String,

    val lives: Int
) {
    constructor(deployedShipDTO: DeployedShipDTO) : this(
        type = deployedShipDTO.type,
        coordinate = CoordinateModel(deployedShipDTO.coordinate),
        orientation = deployedShipDTO.orientation,
        lives = deployedShipDTO.lives
    )

    companion object {
        const val ORIENTATION_REGEX = "HORIZONTAL|VERTICAL"
    }
}
