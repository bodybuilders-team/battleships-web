package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModel
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotDTO

/**
 * A Fired Shot Model.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round in which the shot was made
 * @property result the result of the shot
 * @property sunkShip the ship that was sunk by the shot, or null if the shot didn't sink any ship
 */
data class FiredShotModel(
    val coordinate: CoordinateModel,
    val round: Int,
    val result: ShotResultModel,
    val sunkShip: DeployedShipModel?
) {
    constructor(firedShotDTO: FiredShotDTO) : this(
        coordinate = CoordinateModel(firedShotDTO.coordinate),
        round = firedShotDTO.round,
        result = ShotResultModel(firedShotDTO.result),
        sunkShip = firedShotDTO.sunkShip?.let { DeployedShipModel(it) }
    )
}
