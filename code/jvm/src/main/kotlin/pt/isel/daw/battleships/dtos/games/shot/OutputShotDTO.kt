package pt.isel.daw.battleships.dtos.games.shot

import pt.isel.daw.battleships.database.model.Shot
import pt.isel.daw.battleships.dtos.games.CoordinateDTO

/**
 * Represents an output shot DTO.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round of the shot
 * @property result the result of the shot
 */
data class OutputShotDTO(
    val coordinate: CoordinateDTO,
    val round: Int,
    val result: ShotResultDTO
) {
    constructor(shot: Shot) : this(
        coordinate = CoordinateDTO(shot.coordinate),
        round = shot.round,
        result = ShotResultDTO(shot.result)
    )
}
