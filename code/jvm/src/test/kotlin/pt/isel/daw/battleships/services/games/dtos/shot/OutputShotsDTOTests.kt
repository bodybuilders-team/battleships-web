package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test

class OutputShotsDTOTests {

    @Test
    fun `OutputShotsDTO creation is successful`() {
        OutputShotsDTO(
            shots = listOf(
                OutputShotDTO(
                    coordinate = CoordinateDTO('A', 1),
                    round = 1,
                    result = ShotResultDTO("HIT")
                )
            )
        )
    }
}
