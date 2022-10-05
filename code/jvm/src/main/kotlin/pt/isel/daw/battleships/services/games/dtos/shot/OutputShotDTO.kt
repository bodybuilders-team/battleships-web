package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.shot.Shot

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