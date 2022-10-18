package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.shot.InputShotDTO
import pt.isel.daw.battleships.dtos.games.shot.InputShotsDTO
import kotlin.test.Test

class InputShotsDTOTests {

    @Test
    fun `InputShotsDTO creation is successful`() {
        InputShotsDTO(shots = listOf(InputShotDTO(CoordinateDTO('A', 1))))
    }
}
