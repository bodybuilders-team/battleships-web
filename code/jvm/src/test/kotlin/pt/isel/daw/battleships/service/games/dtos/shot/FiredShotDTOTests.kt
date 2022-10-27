package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.games.ShotTests.Companion.defaultShot
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTOTests.Companion.defaultCoordinateDTO
import pt.isel.daw.battleships.service.games.dtos.shot.ShotResultDTOTests.Companion.defaultShotResultDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class FiredShotDTOTests {

    @Test
    fun `FiredShotDTO creation is successful`() {
        FiredShotDTO(
            coordinate = defaultCoordinateDTO,
            round = 1,
            result = defaultShotResultDTO
        )
    }

    @Test
    fun `FiredShotDTO from Shot conversion is successful`() {
        val shot = defaultShot

        val firedShotDTO = FiredShotDTO(shot)

        assertEquals(CoordinateDTO(shot.coordinate), firedShotDTO.coordinate)
        assertEquals(shot.round, firedShotDTO.round)
        assertEquals(ShotResultDTO(shot.result), firedShotDTO.result)
    }

    companion object {
        val defaultFiredShotDTO
            get() = FiredShotDTO(
                coordinate = defaultCoordinateDTO,
                round = 1,
                result = defaultShotResultDTO
            )
    }
}
