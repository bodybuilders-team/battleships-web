package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.games.models.ship.ShipTypeModel
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * Represents a game configuration.
 *
 * @property gridSize the size of the grid
 * @property maxTimeForLayoutPhase the maximum time allowed for the layout phase
 * @property shotsPerRound the number of shots that can be fired per round
 * @property maxTimePerShot the maximum time allowed for each shot
 * @property shipTypes the types of ships that can be placed in the game
 */
data class GameConfigModel(
    @Min(value = MIN_GRID_SIZE.toLong())
    @Max(value = MAX_GRID_SIZE.toLong())
    val gridSize: Int,
    val maxTimeForLayoutPhase: Int,
    val shotsPerRound: Int,
    val maxTimePerShot: Int,
    val shipTypes: List<ShipTypeModel>
) {
    constructor(gameConfig: GameConfigDTO) : this(
        gridSize = gameConfig.gridSize,
        maxTimeForLayoutPhase = gameConfig.maxTimeForLayoutPhase,
        shotsPerRound = gameConfig.shotsPerRound,
        maxTimePerShot = gameConfig.maxTimePerShot,
        shipTypes = gameConfig.shipTypes.map { ShipTypeModel(it) }
    )

    /**
     * Converts the game config model to a DTO.
     *
     * @return the game config DTO
     */
    fun toGameConfigDTO(): GameConfigDTO =
        GameConfigDTO(
            gridSize = gridSize,
            maxTimeForLayoutPhase = maxTimeForLayoutPhase,
            shotsPerRound = shotsPerRound,
            maxTimePerShot = maxTimePerShot,
            shipTypes = shipTypes.map { it.toShipTypeDTO() }
        )

    companion object {
        private const val MIN_GRID_SIZE = 7
        private const val MAX_GRID_SIZE = 26
    }
}
