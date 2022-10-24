package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModelTests.Companion.defaultCoordinateModel
import kotlin.test.Test
import kotlin.test.assertEquals

class UnfiredShotModelTests {

    @Test
    fun `UnfiredShotModel creation is successful`() {
        UnfiredShotModel(coordinate = defaultCoordinateModel)
    }

    @Test
    fun `UnfiredShotModel to UnfiredShotDTO conversion is successful`() {
        val unfiredShotModel = defaultUnfiredShotModel

        val unfiredShotDTO = unfiredShotModel.toUnfiredShotDTO()

        assertEquals(unfiredShotModel.coordinate.toCoordinateDTO(), unfiredShotDTO.coordinate)
    }

    companion object {
        val defaultUnfiredShotModel
            get() = UnfiredShotModel(coordinate = defaultCoordinateModel)
    }
}
