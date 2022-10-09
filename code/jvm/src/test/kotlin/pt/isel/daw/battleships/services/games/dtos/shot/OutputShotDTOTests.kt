package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.Coordinate
import pt.isel.daw.battleships.database.model.Shot
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputShotDTOTests {
    @Test
    fun `OutputShotDTO creation is successful`() {
        OutputShotDTO(CoordinateDTO('A', 1), 1, ShotResultDTO("HIT"))
    }

    @Test
    fun `OutputShotDTO from Shot conversion is successful`() {
        val shot = Shot(Coordinate('A', 1), 1, Shot.ShotResult.HIT)
        val outputShotDTO = OutputShotDTO(shot)

        assertEquals(CoordinateDTO(shot.coordinate), outputShotDTO.coordinate)
        assertEquals(shot.round, outputShotDTO.round)
        assertEquals(ShotResultDTO(shot.result), outputShotDTO.result)
    }
}
