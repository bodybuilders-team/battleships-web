package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.Shot
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO

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
        CoordinateDTO(shot.coordinate),
        shot.round,
        ShotResultDTO(shot.result)
    )
}
