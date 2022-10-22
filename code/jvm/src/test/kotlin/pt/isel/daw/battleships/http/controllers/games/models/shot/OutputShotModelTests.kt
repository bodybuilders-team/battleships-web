package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.shot.OutputShotDTO
import pt.isel.daw.battleships.dtos.games.shot.ShotResultDTO
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputShotModelTests {

    @Test
    fun `OutputShotModel creation is successful`() {
        OutputShotModel(
            coordinate = CoordinateModel('A', 1),
            round = 1,
            result = ShotResultModel("HIT")
        )
    }

    @Test
    fun `OutputShotModel from OutputShotDTO conversion is successful`() {
        val outputShotDTO = OutputShotDTO(
            coordinate = CoordinateDTO('A', 1),
            round = 1,
            result = ShotResultDTO("HIT")
        )
        val outputShotModel = OutputShotModel(outputShotDTO)

        assertEquals(CoordinateModel(outputShotDTO.coordinate), outputShotModel.coordinate)
        assertEquals(outputShotDTO.round, outputShotModel.round)
        assertEquals(ShotResultModel(outputShotDTO.result), outputShotModel.result)
    }
}
