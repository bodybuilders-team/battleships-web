package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO

/**
 * An Undeployed Ship DTO.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 */
data class UndeployedShipDTO(
    val type: String,
    val coordinate: CoordinateDTO,
    val orientation: String
)
