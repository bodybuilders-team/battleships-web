package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
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
    @Pattern(regexp = "[A-Z]", message = "Col must be between 'A' and 'Z'")
    val col: Char,

    @Min(MIN_ROW_SIZE.toLong(), message = "Row must be greater than $MIN_COL_SIZE")
    @Max(MAX_ROW_SIZE.toLong(), message = "Row must be less than $MAX_COL_SIZE")
    val row: Int
) {
    constructor(coordinate: CoordinateDTO) : this(coordinate.col, coordinate.row)

    /**
     * Converts the coordinate model to a DTO.
     *
     * @return the coordinate DTO
     */
    fun toCoordinateDTO() = CoordinateDTO(col, row)

    companion object {
        private const val MIN_COL_SIZE = 'A'
        private const val MAX_COL_SIZE = 'Z'
        private const val MIN_ROW_SIZE = 1
        private const val MAX_ROW_SIZE = 26
    }
}
