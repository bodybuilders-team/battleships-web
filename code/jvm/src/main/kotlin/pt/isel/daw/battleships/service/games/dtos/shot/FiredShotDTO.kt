package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.games.Shot
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO

/**
 * Represents a Fired Shot DTO.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round of the shot
 * @property result the result of the shot
 */
data class FiredShotDTO(
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
