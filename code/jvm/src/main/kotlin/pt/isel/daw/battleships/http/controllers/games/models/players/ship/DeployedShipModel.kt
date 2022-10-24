package pt.isel.daw.battleships.http.controllers.games.models.players.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTO

/**
 * Represents a Deployed Ship Model.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 * @property lives the number of lives of the ship
 */
data class DeployedShipModel(
    val type: String,
    val coordinate: CoordinateModel,
    val orientation: String,
    val lives: Int
) {
    constructor(deployedShipDTO: DeployedShipDTO) : this(
        type = deployedShipDTO.type,
        coordinate = CoordinateModel(deployedShipDTO.coordinate),
        orientation = deployedShipDTO.orientation,
        lives = deployedShipDTO.lives
    )
}
