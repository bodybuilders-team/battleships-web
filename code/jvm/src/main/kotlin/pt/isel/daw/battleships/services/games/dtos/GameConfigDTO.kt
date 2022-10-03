package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.game.GameConfig

data class GameConfigDTO(
    val gridSize: Int,

    val maxTimePerShot: Int,

    val shotsPerRound: Int,

    val maxTimeForLayoutPhase: Int,

    val shipTypes: List<ShipTypeDTO>
) {
    constructor(gridSize: GameConfig) : this(
        gridSize = gridSize.gridSize,
        maxTimePerShot = gridSize.maxTimePerShot,
        shotsPerRound = gridSize.shotsPerRound,
        maxTimeForLayoutPhase = gridSize.maxTimeForLayoutPhase,
        shipTypes = gridSize.shipTypes.map { ShipTypeDTO(it) }
    )

    fun toGameConfig(): GameConfig =
        GameConfig(
            gridSize = gridSize,
            maxTimePerShot = maxTimePerShot,
            shotsPerRound = shotsPerRound,
            maxTimeForLayoutPhase = maxTimeForLayoutPhase,
            shipTypes = shipTypes.map { it.toShipType() }
        )
}
