package pt.isel.daw.battleships.controllers.games.models.ship

import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateModelTests {
    @Test
    fun `CoordinateModel creation is successful`() {
        CoordinateModel('A', 1)
    }

    @Test
    fun `CoordinateModel from CoordinateDTO conversion is successful`() {
        val coordinateDTO = CoordinateModel('A', 1).toCoordinateDTO()
        val coordinateModel = CoordinateModel(coordinateDTO)

        assertEquals(coordinateDTO.col, coordinateModel.col)
        assertEquals(coordinateDTO.row, coordinateModel.row)
    }

    @Test
    fun `CoordinateModel to CoordinateDTO conversion is successful`() {
        val coordinateModel = CoordinateModel('A', 1)
        val coordinateDTO = coordinateModel.toCoordinateDTO()

        assertEquals(coordinateModel.col, coordinateDTO.col)
        assertEquals(coordinateModel.row, coordinateDTO.row)
    }
}
