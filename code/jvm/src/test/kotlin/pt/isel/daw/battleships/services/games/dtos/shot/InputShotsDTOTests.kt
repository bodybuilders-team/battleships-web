package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test

class InputShotsDTOTests {

    @Test
    fun `InputShotsDTO creation is successful`() {
        InputShotsDTO(shots = listOf(InputShotDTO(CoordinateDTO('A', 1))))
    }
}
