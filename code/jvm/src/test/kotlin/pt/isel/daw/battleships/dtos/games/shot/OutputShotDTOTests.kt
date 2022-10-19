package pt.isel.daw.battleships.dtos.games.shot

import pt.isel.daw.battleships.database.model.Coordinate
import pt.isel.daw.battleships.database.model.Shot
import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputShotDTOTests {

    @Test
    fun `OutputShotDTO creation is successful`() {
        OutputShotDTO(
            coordinate = CoordinateDTO(col = 'A', row = 1),
            round = 1,
            result = ShotResultDTO(result = "HIT")
        )
    }

    @Test
    fun `OutputShotDTO from Shot conversion is successful`() {
        val shot = Shot(
            coordinate = Coordinate(col = 'A', row = 1),
            round = 1,
            result = Shot.ShotResult.HIT
        )
        val outputShotDTO = OutputShotDTO(shot)

        assertEquals(CoordinateDTO(shot.coordinate), outputShotDTO.coordinate)
        assertEquals(shot.round, outputShotDTO.round)
        assertEquals(ShotResultDTO(shot.result), outputShotDTO.result)
    }
}
