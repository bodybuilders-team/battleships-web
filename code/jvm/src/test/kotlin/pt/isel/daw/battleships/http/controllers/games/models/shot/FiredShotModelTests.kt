package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.FiredShotModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.ShotResultModel
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotDTO
import pt.isel.daw.battleships.service.games.dtos.shot.ShotResultDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class FiredShotModelTests {

    @Test
    fun `OutputShotModel creation is successful`() {
        FiredShotModel(
            coordinate = CoordinateModel('A', 1),
            round = 1,
            result = ShotResultModel("HIT")
        )
    }

    @Test
    fun `OutputShotModel from OutputShotDTO conversion is successful`() {
        val firedShotDTO = FiredShotDTO(
            coordinate = CoordinateDTO('A', 1),
            round = 1,
            result = ShotResultDTO("HIT")
        )
        val firedShotModel = FiredShotModel(firedShotDTO)

        assertEquals(CoordinateModel(firedShotDTO.coordinate), firedShotModel.coordinate)
        assertEquals(firedShotDTO.round, firedShotModel.round)
        assertEquals(ShotResultModel(firedShotDTO.result), firedShotModel.result)
    }
}
