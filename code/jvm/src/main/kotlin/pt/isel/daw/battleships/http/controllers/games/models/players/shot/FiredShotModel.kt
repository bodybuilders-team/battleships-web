package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotDTO

/**
 * Represents a shot in the game.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round in which the shot was made
 * @property result the result of the shot
 */
data class FiredShotModel(
    val coordinate: CoordinateModel,
    val round: Int,
    val result: ShotResultModel
) {
    constructor(firedShotDTO: FiredShotDTO) : this(
        coordinate = CoordinateModel(firedShotDTO.coordinate),
        round = firedShotDTO.round,
        result = ShotResultModel(firedShotDTO.result)
    )
}
