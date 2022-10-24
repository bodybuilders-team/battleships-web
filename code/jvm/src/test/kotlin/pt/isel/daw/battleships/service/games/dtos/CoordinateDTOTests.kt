package pt.isel.daw.battleships.service.games.dtos

import pt.isel.daw.battleships.domain.CoordinateTests.Companion.defaultCoordinate
import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateDTOTests {

    @Test
    fun `CoordinateDTO creation is successful`() {
        CoordinateDTO(col = 'A', row = 1)
    }

    @Test
    fun `CoordinateDTO from Coordinate conversion is successful`() {
        val coordinate = defaultCoordinate

        val coordinateDTO = CoordinateDTO(coordinate)

        assertEquals(coordinate.col, coordinateDTO.col)
        assertEquals(coordinate.row, coordinateDTO.row)
    }

    @Test
    fun `CoordinateDTO to Coordinate conversion is successful`() {
        val coordinateDTO = defaultCoordinateDTO

        val coordinate = coordinateDTO.toCoordinate()

        assertEquals(coordinateDTO.col, coordinate.col)
        assertEquals(coordinateDTO.row, coordinate.row)
    }

    companion object {
        val defaultCoordinateDTO
            get() = CoordinateDTO(col = 'A', row = 1)
    }
}
