package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateModelTests {

    @Test
    fun `CoordinateModel creation is successful`() {
        CoordinateModel(col = 'A', row = 1)
    }

    @Test
    fun `CoordinateModel from CoordinateDTO conversion is successful`() {
        val coordinateDTO = CoordinateModel(col = 'A', row = 1).toCoordinateDTO()
        val coordinateModel = CoordinateModel(coordinateDTO)

        assertEquals(coordinateDTO.col, coordinateModel.col)
        assertEquals(coordinateDTO.row, coordinateModel.row)
    }

    @Test
    fun `CoordinateModel to CoordinateDTO conversion is successful`() {
        val coordinateModel = CoordinateModel(col = 'A', row = 1)
        val coordinateDTO = coordinateModel.toCoordinateDTO()

        assertEquals(coordinateModel.col, coordinateDTO.col)
        assertEquals(coordinateModel.row, coordinateDTO.row)
    }
}
