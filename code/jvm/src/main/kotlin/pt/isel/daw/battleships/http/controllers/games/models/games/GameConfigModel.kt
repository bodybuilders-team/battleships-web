package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.http.controllers.games.models.ShipTypeModel
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTO
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * A Game Config Model.
 *
 * @property gridSize the size of the grid
 * @property maxTimeForLayoutPhase the maximum time allowed for the layout phase
 * @property shotsPerRound the number of shots that can be fired per round
 * @property maxTimePerRound the maximum time allowed for each shot
 * @property shipTypes the types of ships that can be placed in the game
 */
data class GameConfigModel(
    @field:Min(value = MIN_GRID_SIZE.toLong(), message = "The grid size must be at least $MIN_GRID_SIZE")
    @field:Max(value = MAX_GRID_SIZE.toLong(), message = "The grid size must be at most $MAX_GRID_SIZE")
    val gridSize: Int,

    @field:Min(
        value = MIN_MAX_TIME_FOR_LAYOUT_PHASE.toLong(),
        message = "The time must be at least $MIN_MAX_TIME_FOR_LAYOUT_PHASE"
    )
    @field:Max(
        value = MAX_MAX_TIME_FOR_LAYOUT_PHASE.toLong(),
        message = "The time must be at most $MAX_MAX_TIME_FOR_LAYOUT_PHASE"
    )
    val maxTimeForLayoutPhase: Int,

    @field:NotNull(message = "The shots per round must be specified")
    @field:Min(
        value = MIN_SHOTS_PER_ROUND.toLong(),
        message = "The shots per round must be at least $MIN_SHOTS_PER_ROUND"
    )
    @field:Max(
        value = MAX_SHOTS_PER_ROUND.toLong(),
        message = "The shots per round must be at most $MAX_SHOTS_PER_ROUND"
    )
    val shotsPerRound: Int,

    @field:NotNull(message = "The max time per round must be specified")
    @field:Min(value = MIN_MAX_TIME_PER_ROUND.toLong(), message = "The time must be at least $MIN_MAX_TIME_PER_ROUND")
    @field:Max(value = MAX_MAX_TIME_PER_ROUND.toLong(), message = "The time must be at most $MAX_MAX_TIME_PER_ROUND")
    val maxTimePerRound: Int,

    val shipTypes: List<ShipTypeModel>
) {
    constructor(gameConfigDTO: GameConfigDTO) : this(
        gridSize = gameConfigDTO.gridSize,
        maxTimeForLayoutPhase = gameConfigDTO.maxTimeForLayoutPhase,
        shotsPerRound = gameConfigDTO.shotsPerRound,
        maxTimePerRound = gameConfigDTO.maxTimePerRound,
        shipTypes = gameConfigDTO.shipTypes.map { ShipTypeModel(it) }
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
            maxTimePerRound = maxTimePerRound,
            shipTypes = shipTypes.map { it.toShipTypeDTO() }
        )

    companion object {
        private const val MIN_GRID_SIZE = 7
        private const val MAX_GRID_SIZE = 18

        private const val MIN_MAX_TIME_FOR_LAYOUT_PHASE = 10
        private const val MAX_MAX_TIME_FOR_LAYOUT_PHASE = 120

        private const val MIN_SHOTS_PER_ROUND = 1
        private const val MAX_SHOTS_PER_ROUND = 5

        private const val MIN_MAX_TIME_PER_ROUND = 10
        private const val MAX_MAX_TIME_PER_ROUND = 120
    }
}
