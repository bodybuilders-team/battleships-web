package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test

class FiredShotsDTOTests {

    @Test
    fun `OutputShotsDTO creation is successful`() {
        FiredShotsDTO(
            shots = listOf(
                FiredShotDTO(
                    coordinate = CoordinateDTO('A', 1),
                    round = 1,
                    result = ShotResultDTO("HIT")
                )
            )
        )
    }
}
