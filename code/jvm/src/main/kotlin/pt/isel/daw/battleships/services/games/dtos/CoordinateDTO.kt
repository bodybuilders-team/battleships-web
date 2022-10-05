package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.Coordinate

/**
 * Represents a Coordinate DTO.
 *
 * @property col the column of the coordinate
 * @property row the row of the coordinate
 */
data class CoordinateDTO(
    val col: Char,
    val row: Int
) {
    constructor(coordinate: Coordinate) : this(coordinate.col, coordinate.row)

    /**
     * Converts this DTO to a database model.
     *
     * @return the database model coordinate
     */
    fun toCoordinate() = Coordinate(col, row)
}
