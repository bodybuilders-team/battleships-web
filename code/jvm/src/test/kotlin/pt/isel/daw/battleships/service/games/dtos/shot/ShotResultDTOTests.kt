package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.Shot
import kotlin.test.Test

class ShotResultDTOTests {

    @Test
    fun `ShotResultDTO creation is successful`() {
        ShotResultDTO(result = "HIT")
    }

    @Test
    fun `ShotResultDTO creation from ShotResult is successful`() {
        val shotResult = Shot.ShotResult.HIT

        val shotResultDTO = ShotResultDTO(shotResult)

        assert(shotResultDTO.result == shotResult.name)
    }

    companion object {
        val defaultShotResultDTO
            get() = ShotResultDTO(result = "HIT")
    }
}
