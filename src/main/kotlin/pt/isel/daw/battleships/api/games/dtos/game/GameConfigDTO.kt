package pt.isel.daw.battleships.api.games.dtos.game

import pt.isel.daw.battleships.api.games.dtos.ship.ShipTypeDTO
import javax.validation.constraints.Size

/**
 * Represents a game configuration.
 *
 * @property gridSize The size of the grid.
 * @property shipTypes The types of ships that can be placed in the game.
 * @property shotsPerRound The number of shots that can be fired per round.
 * @property maxTimeForLayoutPhase The maximum time allowed for the layout phase.
 * @property maxTimePerShot The maximum time allowed for each shot.
 */
data class GameConfigDTO(
    @Size(min = MIN_GRID_SIZE, max = MAX_GRID_SIZE)
    val gridSize: Int,
    val shipTypes: List<ShipTypeDTO>,
    val shotsPerRound: Int,
    val maxTimeForLayoutPhase: Int,
    val maxTimePerShot: Int
) {
    companion object {
        private const val MIN_GRID_SIZE = 1
        private const val MAX_GRID_SIZE = 26
    }
}
