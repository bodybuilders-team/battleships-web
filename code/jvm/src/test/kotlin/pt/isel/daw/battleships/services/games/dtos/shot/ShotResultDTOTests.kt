package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.Shot
import kotlin.test.Test

class ShotResultDTOTests {
    @Test
    fun `ShotResultDTO creation is successful`() {
        ShotResultDTO("HIT")
    }

    @Test
    fun `ShotResultDTO creation from ShotResult is successful`() {
        val shotResult = Shot.ShotResult.HIT
        val shotResultDTO = ShotResultDTO(shotResult)

        assert(shotResultDTO.result == shotResult.name)
    }
}