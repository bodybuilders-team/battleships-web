package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.services.games.dtos.shot.ShotResultDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class ShotResultModelTests {
    @Test
    fun `ShotResultModel creation is successful`() {
        ShotResultModel("HIT")
    }

    @Test
    fun `ShotResultModel from ShotResultDTO conversion is successful`() {
        val shotResultDTO = ShotResultDTO("HIT")
        val shotResultModel = ShotResultModel(shotResultDTO)

        assertEquals(shotResultDTO.result, shotResultModel.result)
    }

    @Test
    fun `ShotResultModel to ShotResultDTO conversion is successful`() {
        val shotResultModel = ShotResultModel("HIT")
        val shotResultDTO = shotResultModel.toShotResultDTO()

        assertEquals(shotResultModel.result, shotResultDTO.result)
    }
}