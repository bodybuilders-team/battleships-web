package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.controllers.games.models.ship.CoordinateModel
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO

data class OutputShotModel(
    val coordinate: CoordinateModel,
    val round: Int,
    val result: ShotResultModel
) {
    constructor(shotDTO: OutputShotDTO) : this(
        CoordinateModel(shotDTO.coordinate),
        shotDTO.round,
        ShotResultModel(shotDTO.result)
    )
}