package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.api.games.dtos.ship.ShipTypeDTO
import pt.isel.daw.battleships.database.model.game.GameConfig
import javax.validation.constraints.Size

/**
 * Represents a game configuration.
 *
 * @property gridSize The size of the grid.
 * @property maxTimeForLayoutPhase The maximum time allowed for the layout phase.
 * @property shotsPerRound The number of shots that can be fired per round.
 * @property maxTimePerShot The maximum time allowed for each shot.
 * @property shipTypes The types of ships that can be placed in the game.
 */
data class GameConfigDTO(
    @Size(min = MIN_GRID_SIZE, max = MAX_GRID_SIZE)
    val gridSize: Int,
    val maxTimeForLayoutPhase: Int,
    val shotsPerRound: Int,
    val maxTimePerShot: Int,
    val shipTypes: List<ShipTypeDTO>
) {
    constructor(gameConfig: GameConfig) : this(
        gameConfig.gridSize,
        gameConfig.maxTimeForLayoutPhase,
        gameConfig.shotsPerRound,
        gameConfig.maxTimePerShot,
        gameConfig.shipTypes.map { ShipTypeDTO(it) }
    )

    companion object {
        private const val MIN_GRID_SIZE = 7
        private const val MAX_GRID_SIZE = 26
    }
}
