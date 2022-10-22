package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO

/**
 * Ship DTO for services.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 * @property lives the number of lives of the ship
 */
data class OutputShipDTO(
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
