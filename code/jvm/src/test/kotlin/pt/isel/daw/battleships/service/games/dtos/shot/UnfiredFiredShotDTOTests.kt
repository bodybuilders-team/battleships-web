package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test

class UnfiredFiredShotDTOTests {

    @Test
    fun `InputShotDTO creation is successful`() {
        UnfiredShotDTO(coordinate = CoordinateDTO(col = 'A', row = 1))
    }
}
