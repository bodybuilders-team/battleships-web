package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.controllers.games.models.ship.CoordinateModel
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO
import pt.isel.daw.battleships.services.games.dtos.shot.ShotResultDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputShotModelTests {
    @Test
    fun `OutputShotModel creation is successful`() {
        OutputShotModel(
            CoordinateModel('A', 1),
            1,
            ShotResultModel("HIT")
        )
    }

    @Test
    fun `OutputShotModel from OutputShotDTO conversion is successful`() {
        val outputShotDTO = OutputShotDTO(
            CoordinateDTO('A', 1),
            1,
            ShotResultDTO("HIT")
        )
        val outputShotModel = OutputShotModel(outputShotDTO)

        assertEquals(CoordinateModel(outputShotDTO.coordinate), outputShotModel.coordinate)
        assertEquals(outputShotDTO.round, outputShotModel.round)
        assertEquals(ShotResultModel(outputShotDTO.result), outputShotModel.result)
    }
}