package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.Coordinate
import kotlin.test.Test

class CoordinateDTOTests {
    @Test
    fun `CoordinateDTO creation is successful`() {
        CoordinateDTO('A', 1)
    }

    @Test
    fun `CoordinateDTO from Coordinate conversion is successful`() {
        val coordinate = Coordinate('A', 1)
        val coordinateDTO = CoordinateDTO(coordinate)

        assert(coordinateDTO.col == coordinate.col)
        assert(coordinateDTO.row == coordinate.row)
    }

    @Test
    fun `CoordinateDTO to Coordinate conversion is successful`() {
        val coordinateDTO = CoordinateDTO('A', 1)
        val coordinate = coordinateDTO.toCoordinate()

        assert(coordinate.col == coordinateDTO.col)
        assert(coordinate.row == coordinateDTO.row)
    }
}