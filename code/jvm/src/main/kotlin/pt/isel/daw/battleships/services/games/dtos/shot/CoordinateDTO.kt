package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.Coordinate

data class CoordinateDTO(
    val col: Char,
    val row: Int
) {
    constructor(coordinate: Coordinate) : this(coordinate.col, coordinate.row)

    fun toCoordinate() = Coordinate(col, row)
}