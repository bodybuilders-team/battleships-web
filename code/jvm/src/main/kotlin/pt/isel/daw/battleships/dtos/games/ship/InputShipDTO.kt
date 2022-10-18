package pt.isel.daw.battleships.dtos.games.ship

import pt.isel.daw.battleships.dtos.games.CoordinateDTO

/**
 * Ship DTO for services.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 */
data class InputShipDTO(
    val type: String,
    val coordinate: CoordinateDTO,
    val orientation: String
)
