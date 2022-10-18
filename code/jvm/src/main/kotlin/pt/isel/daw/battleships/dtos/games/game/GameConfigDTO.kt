package pt.isel.daw.battleships.dtos.games.game

import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.dtos.games.ship.ShipTypeDTO

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
    constructor(gridSize: GameConfig) : this(
        gridSize = gridSize.gridSize,
        maxTimeForLayoutPhase = gridSize.maxTimeForLayoutPhase,
        shotsPerRound = gridSize.shotsPerRound,
        maxTimePerRound = gridSize.maxTimePerRound,
        shipTypes = gridSize.shipTypes.map { ShipTypeDTO(it) }
    )

    /**
     * Converts this DTO to a database model.
     *
     * @return the database model game config
     */
    fun toGameConfig(): GameConfig =
        GameConfig(
            gridSize = gridSize,
            maxTimeForLayoutPhase = maxTimeForLayoutPhase,
            shotsPerRound = shotsPerRound,
            maxTimePerRound = maxTimePerRound,
            shipTypes = shipTypes.map { it.toShipType() }
        )
}
