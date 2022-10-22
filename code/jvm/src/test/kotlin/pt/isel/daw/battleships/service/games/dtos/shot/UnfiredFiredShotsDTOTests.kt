package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test

class UnfiredFiredShotsDTOTests {

    @Test
    fun `InputShotsDTO creation is successful`() {
        UnfiredShotsDTO(shots = listOf(UnfiredShotDTO(CoordinateDTO('A', 1))))
    }
}
