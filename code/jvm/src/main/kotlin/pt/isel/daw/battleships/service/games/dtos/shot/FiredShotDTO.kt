package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.games.Shot
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTO

/**
 * A Fired Shot DTO.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round of the shot
 * @property result the result of the shot
 * @property sunkShip the ship that was sunk by the shot, or null if the shot didn't sink any ship
 */
data class FiredShotDTO(
    val coordinate: CoordinateDTO,
    val round: Int,
    val result: ShotResultDTO,
    val sunkShip: DeployedShipDTO?
) {
    constructor(shot: Shot) : this(
        coordinate = CoordinateDTO(shot.coordinate),
        round = shot.round,
        result = ShotResultDTO(shot.result),
        sunkShip = shot.sunkShip?.let { DeployedShipDTO(it) }
    )
}
