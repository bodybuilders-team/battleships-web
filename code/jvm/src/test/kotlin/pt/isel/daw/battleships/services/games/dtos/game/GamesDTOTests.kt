package pt.isel.daw.battleships.services.games.dtos.game

import kotlin.test.Test

class GamesDTOTests {
    @Test
    fun `GamesDTO creation is successful`() {
        GamesDTO(
            listOf(
                GameDTO(
                    1,
                    "name",
                    "creator",
                    GameConfigDTO(1, 2, 3, 4, listOf()),
                    GameStateDTO("WAITING_FOR_PLAYERS", 1, null, null),
                    listOf()
                )
            )
        )
    }
}
