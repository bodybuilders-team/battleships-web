package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotDTOTests.Companion.defaultFiredShotDTO
import kotlin.test.Test

class FiredShotsDTOTests {

    @Test
    fun `FiredShotsDTO creation is successful`() {
        FiredShotsDTO(
            shots = listOf(defaultFiredShotDTO)
        )
    }

    companion object {
        val defaultFiredShotsDTO
            get() = FiredShotsDTO(
                shots = listOf(defaultFiredShotDTO)
            )
    }
}
