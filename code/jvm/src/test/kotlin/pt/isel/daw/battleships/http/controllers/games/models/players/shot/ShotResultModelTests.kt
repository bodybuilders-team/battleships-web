package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.service.games.dtos.shot.ShotResultDTOTests.Companion.defaultShotResultDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class ShotResultModelTests {

    @Test
    fun `ShotResultModel creation is successful`() {
        ShotResultModel(result = "HIT")
    }

    @Test
    fun `ShotResultModel from ShotResultDTO conversion is successful`() {
        val shotResultDTO = defaultShotResultDTO

        val shotResultModel = ShotResultModel(shotResultDTO)

        assertEquals(shotResultDTO.result, shotResultModel.result)
    }

    @Test
    fun `ShotResultModel to ShotResultDTO conversion is successful`() {
        val shotResultModel = defaultShotResultModel

        val shotResultDTO = shotResultModel.toShotResultDTO()

        assertEquals(shotResultModel.result, shotResultDTO.result)
    }

    companion object {
        val defaultShotResultModel
            get() = ShotResultModel(result = "HIT")
    }
}
