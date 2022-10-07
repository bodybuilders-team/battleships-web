package pt.isel.daw.battleships.services.games.dtos.game

import kotlin.test.Test

class MatchmakeDTOTests {
    @Test
    fun `MatchmakeDTO creation is successful`() {
        MatchmakeDTO(
            GameDTO(
                1, "name", "creator",
                GameConfigDTO(1, 2, 3, 4, listOf()),
                GameStateDTO("WAITING_FOR_PLAYERS", 1, null, null),
                listOf()
            ),
            true
        )
    }
}