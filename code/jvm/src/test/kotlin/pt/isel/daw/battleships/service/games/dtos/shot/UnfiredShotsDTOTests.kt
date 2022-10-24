package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.shot.UnfiredShotDTOTests.Companion.defaultUnfiredShotDTO
import kotlin.test.Test

class UnfiredShotsDTOTests {

    @Test
    fun `UnfiredShotsDTO creation is successful`() {
        UnfiredShotsDTO(shots = listOf(defaultUnfiredShotDTO))
    }

    companion object {
        val defaultUnfiredShotsDTO
            get() = UnfiredShotsDTO(shots = listOf(defaultUnfiredShotDTO))
    }
}
