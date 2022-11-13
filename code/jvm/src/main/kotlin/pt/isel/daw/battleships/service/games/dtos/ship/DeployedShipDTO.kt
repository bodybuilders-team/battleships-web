package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.domain.games.ship.DeployedShip
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO

/**
 * A Deployed Ship DTO.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 * @property lives the number of lives of the ship
 */
data class DeployedShipDTO(
    val type: String,
    val coordinate: CoordinateDTO,
    val orientation: String,
    val lives: Int
) {
    constructor(deployedShip: DeployedShip) : this(
        type = deployedShip.type.shipName,
        coordinate = CoordinateDTO(deployedShip.coordinate),
        orientation = deployedShip.orientation.name,
        lives = deployedShip.lives
    )
}
