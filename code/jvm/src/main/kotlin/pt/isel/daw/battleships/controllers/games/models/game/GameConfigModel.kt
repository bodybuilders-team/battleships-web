package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.games.models.ship.ShipTypeModel
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import javax.validation.constraints.Size

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
    @Size(min = MIN_GRID_SIZE, max = MAX_GRID_SIZE)
    val gridSize: Int,
    val maxTimeForLayoutPhase: Int,
    val shotsPerRound: Int,
    val maxTimePerShot: Int,
    val shipTypes: List<ShipTypeModel>
) {
    constructor(gameConfig: GameConfigDTO) : this(
        gameConfig.gridSize,
        gameConfig.maxTimeForLayoutPhase,
        gameConfig.shotsPerRound,
        gameConfig.maxTimePerShot,
        gameConfig.shipTypes.map { ShipTypeModel(it) }
    )

    /**
     * Converts the game config model to a DTO.
     *
     * @return the game config DTO
     */
    fun toGameConfigDTO(): GameConfigDTO =
        GameConfigDTO(
            gridSize,
            maxTimeForLayoutPhase,
            shotsPerRound,
            maxTimePerShot,
            shipTypes.map { it.toShipTypeDTO() }
        )

    companion object {
        private const val MIN_GRID_SIZE = 7
        private const val MAX_GRID_SIZE = 26
    }
}
