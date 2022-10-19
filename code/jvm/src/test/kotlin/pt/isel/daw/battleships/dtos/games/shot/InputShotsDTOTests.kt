package pt.isel.daw.battleships.dtos.games.shot

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import kotlin.test.Test

class InputShotsDTOTests {

    @Test
    fun `InputShotsDTO creation is successful`() {
        InputShotsDTO(shots = listOf(InputShotDTO(CoordinateDTO('A', 1))))
    }
}
