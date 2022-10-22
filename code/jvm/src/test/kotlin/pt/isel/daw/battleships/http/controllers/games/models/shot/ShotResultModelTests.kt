package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.ShotResultModel
import pt.isel.daw.battleships.service.games.dtos.shot.ShotResultDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class ShotResultModelTests {

    @Test
    fun `ShotResultModel creation is successful`() {
        ShotResultModel(result = "HIT")
    }

    @Test
    fun `ShotResultModel from ShotResultDTO conversion is successful`() {
        val shotResultDTO = ShotResultDTO(result = "HIT")
        val shotResultModel = ShotResultModel(shotResultDTO)

        assertEquals(shotResultDTO.result, shotResultModel.result)
    }

    @Test
    fun `ShotResultModel to ShotResultDTO conversion is successful`() {
        val shotResultModel = ShotResultModel(result = "HIT")
        val shotResultDTO = shotResultModel.toShotResultDTO()

        assertEquals(shotResultModel.result, shotResultDTO.result)
    }
}
