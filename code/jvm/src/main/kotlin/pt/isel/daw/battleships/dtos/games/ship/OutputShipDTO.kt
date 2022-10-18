package pt.isel.daw.battleships.dtos.games.ship

import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.dtos.games.CoordinateDTO

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
    constructor(ship: Ship) : this(
        type = ship.type.shipName,
        coordinate = CoordinateDTO(ship.coordinate),
        orientation = ship.orientation.name,
        lives = ship.lives
    )
}
