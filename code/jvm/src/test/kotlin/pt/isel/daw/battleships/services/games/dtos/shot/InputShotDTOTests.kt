package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test

class InputShotDTOTests {
    @Test
    fun `InputShotDTO creation is successful`() {
        InputShotDTO(CoordinateDTO('A', 1))
    }
}