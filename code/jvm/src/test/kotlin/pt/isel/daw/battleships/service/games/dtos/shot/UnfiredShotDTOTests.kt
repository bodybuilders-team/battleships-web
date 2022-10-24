package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTOTests.Companion.defaultCoordinateDTO
import kotlin.test.Test

class UnfiredShotDTOTests {

    @Test
    fun `UnfiredShotDTO creation is successful`() {
        UnfiredShotDTO(coordinate = defaultCoordinateDTO)
    }

    companion object {
        val defaultUnfiredShotDTO
            get() = UnfiredShotDTO(coordinate = defaultCoordinateDTO)
    }
}
