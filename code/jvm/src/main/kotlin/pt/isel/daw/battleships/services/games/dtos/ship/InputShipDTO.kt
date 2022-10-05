package pt.isel.daw.battleships.services.games.dtos.ship

import pt.isel.daw.battleships.services.games.dtos.shot.CoordinateDTO

/**
 * Ship DTO for services.
 *
 * Doesn't know the data regarding the ShipType, apart from the name (its identifier), as that's governed by the
 * game config and its variable for each game.
 */
data class InputShipDTO(
    val type: String,
    val coordinate: CoordinateDTO,
    val orientation: Char?
)