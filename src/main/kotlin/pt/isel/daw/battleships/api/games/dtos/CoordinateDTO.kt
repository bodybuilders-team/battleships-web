package pt.isel.daw.battleships.api.games.dtos

import javax.validation.constraints.Size

const val MAX_ROW_SIZE = 26

data class CoordinateDTO(
    val col: Char,
    @get:Size(min = 1, max = MAX_ROW_SIZE, message = "Row must be between 1 and $MAX_ROW_SIZE")
    val row: Int
)
