package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.services.games.dtos.shot.ShotResultDTO

data class ShotResultModel(val result: String) {
    constructor(shotResultDTO: ShotResultDTO) : this(shotResultDTO.result)

    fun toShotResultDTO() = ShotResultDTO(result)
}