package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.domain.games.game.GameConfig
import pt.isel.daw.battleships.service.games.dtos.ship.ShipTypeDTO

/**
 * Represents a Game Config DTO.
 *
 * @property gridSize the size of the grid
 * @property maxTimePerRound the maximum time per round
 * @property shotsPerRound the number of shots per round
 * @property maxTimeForLayoutPhase the maximum time for the layout phase
 * @property shipTypes the ship types to be placed in the layout phase
 */
data class GameConfigDTO(
    val gridSize: Int,
    val maxTimeForLayoutPhase: Int,
    val shotsPerRound: Int,
    val maxTimePerRound: Int,
    val shipTypes: List<ShipTypeDTO>
) {
    constructor(gameConfig: GameConfig) : this(
        gridSize = gameConfig.gridSize,
        maxTimeForLayoutPhase = gameConfig.maxTimeForLayoutPhase,
        shotsPerRound = gameConfig.shotsPerRound,
        maxTimePerRound = gameConfig.maxTimePerRound,
        shipTypes = gameConfig.shipTypes.map { ShipTypeDTO(it) }
    )

    /**
     * Converts this DTO to a database model.
     *
     * @return the database model game config
     */
    fun toGameConfig(): GameConfig =
        GameConfig(
            gridSize = gridSize,
            maxTimePerRound = maxTimePerRound,
            maxTimeForLayoutPhase = maxTimeForLayoutPhase,
            shotsPerRound = shotsPerRound,
            shipTypes = shipTypes.map { it.toShipType() }
        )
}
