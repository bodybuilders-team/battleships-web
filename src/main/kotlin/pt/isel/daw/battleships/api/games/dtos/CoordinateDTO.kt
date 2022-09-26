package pt.isel.daw.battleships.api.games.dtos

import javax.validation.constraints.Size

/**
 * Represents a coordinate in the game board.
 *
 * @property col the column of the coordinate
 * @property row the row of the coordinate
 */
data class CoordinateDTO(
    val col: Char,

    @get:Size(
        min = MIN_ROW_SIZE,
        max = MAX_ROW_SIZE,
        message = "Row must be between $MIN_ROW_SIZE and $MAX_ROW_SIZE"
    )
    val row: Int
) {
    companion object {
        private const val MAX_ROW_SIZE = 26
        private const val MIN_ROW_SIZE = 1
    }
}
