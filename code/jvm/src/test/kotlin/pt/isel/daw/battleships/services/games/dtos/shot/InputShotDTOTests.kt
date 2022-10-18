package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.shot.InputShotDTO
import kotlin.test.Test

class InputShotDTOTests {

    @Test
    fun `InputShotDTO creation is successful`() {
        InputShotDTO(coordinate = CoordinateDTO(col = 'A', row = 1))
    }
}
