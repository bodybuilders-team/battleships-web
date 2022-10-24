package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.service.games.dtos.game.GameDTOTests.Companion.defaultGameDTO
import kotlin.test.Test

class MatchmakeResponseDTOTests {

    @Test
    fun `MatchmakeResponseDTO creation is successful`() {
        MatchmakeResponseDTO(
            game = defaultGameDTO,
            wasCreated = true
        )
    }

    companion object {
        val defaultMatchmakeResponseDTO
            get() = MatchmakeResponseDTO(
                game = defaultGameDTO,
                wasCreated = true
            )
    }
}
