package pt.isel.daw.battleships.api.games.dtos

import javax.validation.constraints.Size

data class GameConfigDTO(
    @Size(min = 1, max = 10)
    val gridSize: Int,
    val shipTypes: List<ShipTypeDTO>,
    val numberOfShotsPerRound: Int,
    val maximumTimeForLayoutPhase: Int,
    val maximumTimePerShot: Int
)
