package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.GameConfigDTO
import pt.isel.daw.battleships.http.controllers.games.models.ship.ShipTypeModel
import javax.validation.constraints.Max
import javax.validation.constraints.Min
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
    @Min(value = MIN_GRID_SIZE.toLong(), message = "The grid size must be at least $MIN_GRID_SIZE")
    @Max(value = MAX_GRID_SIZE.toLong(), message = "The grid size must be at most $MAX_GRID_SIZE")
    val gridSize: Int,

    @Min(
        value = MIN_MAX_TIME_FOR_LAYOUT_PHASE.toLong(),
        message = "The time must be at least $MIN_MAX_TIME_FOR_LAYOUT_PHASE"
    )
    @Max(
        value = MAX_MAX_TIME_FOR_LAYOUT_PHASE.toLong(),
        message = "The time must be at most $MAX_MAX_TIME_FOR_LAYOUT_PHASE"
    )
    val maxTimeForLayoutPhase: Int,

    @Min(value = MIN_SHOTS_PER_ROUND.toLong(), message = "The shots per round must be at least $MIN_SHOTS_PER_ROUND")
    @Max(value = MAX_SHOTS_PER_ROUND.toLong(), message = "The shots per round must be at most $MAX_SHOTS_PER_ROUND")
    val shotsPerRound: Int,

    @Min(value = MIN_MAX_TIME_PER_SHOT.toLong(), message = "The time must be at least $MIN_MAX_TIME_PER_SHOT")
    @Max(value = MAX_MAX_TIME_PER_SHOT.toLong(), message = "The time must be at most $MAX_MAX_TIME_PER_SHOT")
    val maxTimePerShot: Int,

    @Size(min = MIN_SHIP_TYPE_COUNT, message = "There must be at least $MIN_SHIP_TYPE_COUNT ship types")
    val shipTypes: List<ShipTypeModel>
) {
    constructor(gameConfig: GameConfigDTO) : this(
        gridSize = gameConfig.gridSize,
        maxTimeForLayoutPhase = gameConfig.maxTimeForLayoutPhase,
        shotsPerRound = gameConfig.shotsPerRound,
        maxTimePerShot = gameConfig.maxTimePerRound,
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
            maxTimePerRound = maxTimePerShot,
            shipTypes = shipTypes.map { it.toShipTypeDTO() }
        )

    companion object {
        private const val MIN_GRID_SIZE = 7
        private const val MAX_GRID_SIZE = 18

        private const val MIN_MAX_TIME_FOR_LAYOUT_PHASE = 10
        private const val MAX_MAX_TIME_FOR_LAYOUT_PHASE = 120

        private const val MIN_SHOTS_PER_ROUND = 1
        private const val MAX_SHOTS_PER_ROUND = 5

        private const val MIN_MAX_TIME_PER_SHOT = 10
        private const val MAX_MAX_TIME_PER_SHOT = 120

        private const val MIN_SHIP_TYPE_COUNT = 1
    }
}