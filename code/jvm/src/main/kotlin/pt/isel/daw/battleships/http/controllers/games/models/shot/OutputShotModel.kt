package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.dtos.games.shot.OutputShotDTO
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO

/**
 * Represents a shot in the game.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round in which the shot was made
 * @property result the result of the shot
 */
data class OutputShotModel(
    val coordinate: CoordinateModel,
    val round: Int,
    val result: ShotResultModel
) {
    constructor(shotDTO: OutputShotDTO) : this(
        coordinate = CoordinateModel(shotDTO.coordinate),
        round = shotDTO.round,
        result = ShotResultModel(shotDTO.result)
    )
}
