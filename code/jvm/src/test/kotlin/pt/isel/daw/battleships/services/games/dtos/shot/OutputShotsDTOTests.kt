package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.shot.OutputShotDTO
import pt.isel.daw.battleships.dtos.games.shot.OutputShotsDTO
import pt.isel.daw.battleships.dtos.games.shot.ShotResultDTO
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
