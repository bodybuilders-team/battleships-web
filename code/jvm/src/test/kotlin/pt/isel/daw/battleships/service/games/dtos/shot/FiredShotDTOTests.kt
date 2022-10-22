package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.Shot
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class FiredShotDTOTests {

    @Test
    fun `OutputShotDTO creation is successful`() {
        FiredShotDTO(
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
        val firedShotDTO = FiredShotDTO(shot)

        assertEquals(CoordinateDTO(shot.coordinate), firedShotDTO.coordinate)
        assertEquals(shot.round, firedShotDTO.round)
        assertEquals(ShotResultDTO(shot.result), firedShotDTO.result)
    }
}
