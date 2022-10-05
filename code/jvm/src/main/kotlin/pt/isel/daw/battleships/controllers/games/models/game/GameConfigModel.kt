package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.games.models.ship.ShipTypeModel
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
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
data class GameConfigModel(
    @Size(min = MIN_GRID_SIZE, max = MAX_GRID_SIZE)
    val gridSize: Int,
    val maxTimeForLayoutPhase: Int,
    val shotsPerRound: Int,
    val maxTimePerShot: Int,
    val shipTypes: List<ShipTypeModel>
) {
    fun toGameConfigDTO(): GameConfigDTO =
        GameConfigDTO(
            gridSize,
            maxTimeForLayoutPhase,
            shotsPerRound,
            maxTimePerShot,
            shipTypes.map { it.toShipTypeDTO() }
        )

    constructor(gameConfig: GameConfigDTO) : this(
        gameConfig.gridSize,
        gameConfig.maxTimeForLayoutPhase,
        gameConfig.shotsPerRound,
        gameConfig.maxTimePerShot,
        gameConfig.shipTypes.map { ShipTypeModel(it) }
    )

    companion object {
        private const val MIN_GRID_SIZE = 7
        private const val MAX_GRID_SIZE = 26
    }
}
