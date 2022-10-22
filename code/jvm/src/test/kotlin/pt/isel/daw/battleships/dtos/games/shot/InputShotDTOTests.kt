package pt.isel.daw.battleships.dtos.games.shot

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test

class InputShotDTOTests {

    @Test
    fun `InputShotDTO creation is successful`() {
        InputShotDTO(coordinate = CoordinateDTO(col = 'A', row = 1))
    }
}
