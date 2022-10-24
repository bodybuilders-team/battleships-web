package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModelTests.Companion.defaultCoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.players.shot.ShotResultModelTests.Companion.defaultShotResultModel
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotDTOTests.Companion.defaultFiredShotDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class FiredShotModelTests {

    @Test
    fun `FiredShotModel creation is successful`() {
        FiredShotModel(
            coordinate = defaultCoordinateModel,
            round = 1,
            result = defaultShotResultModel
        )
    }

    @Test
    fun `FiredShotModel from FiredShotDTO conversion is successful`() {
        val firedShotDTO = defaultFiredShotDTO

        val firedShotModel = FiredShotModel(firedShotDTO = firedShotDTO)

        assertEquals(CoordinateModel(firedShotDTO.coordinate), firedShotModel.coordinate)
        assertEquals(firedShotDTO.round, firedShotModel.round)
        assertEquals(ShotResultModel(firedShotDTO.result), firedShotModel.result)
    }

    companion object {
        val defaultFiredShotModel
            get() = FiredShotModel(
                coordinate = defaultCoordinateModel,
                round = 1,
                result = defaultShotResultModel
            )
    }
}
