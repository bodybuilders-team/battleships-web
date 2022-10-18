package pt.isel.daw.battleships.http.controllers.games.models

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

/**
 * Represents a coordinate in the game board.
 *
 * @property col the column of the coordinate
 * @property row the row of the coordinate
 */
data class CoordinateModel(
    @Pattern(regexp = COL_REGEX, message = "Column must be between $MIN_COL and $MAX_COL.")
    val col: Char,

    @Min(MIN_ROW_SIZE.toLong(), message = "Row must be greater than $MIN_ROW_SIZE")
    @Max(MAX_ROW_SIZE.toLong(), message = "Row must be less than $MAX_ROW_SIZE")
    val row: Int
) {
    constructor(coordinate: CoordinateDTO) : this(
        col = coordinate.col,
        row = coordinate.row
    )

    /**
     * Converts the coordinate model to a DTO.
     *
     * @return the coordinate DTO
     */
    fun toCoordinateDTO() = CoordinateDTO(col = col, row = row)

    companion object {
        private const val MIN_COL = 'A'
        private const val MAX_COL = 'R'
        private const val COL_REGEX = "[$MIN_COL-$MAX_COL]"

        private const val MIN_ROW_SIZE = 1
        private const val MAX_ROW_SIZE = 18
    }
}
