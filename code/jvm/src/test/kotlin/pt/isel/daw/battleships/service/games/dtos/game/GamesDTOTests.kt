package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.service.games.dtos.game.GameDTOTests.Companion.defaultGameDTO
import kotlin.test.Test

class GamesDTOTests {

    @Test
    fun `GamesDTO creation is successful`() {
        GamesDTO(
            games = listOf(defaultGameDTO),
            totalCount = 1
        )
    }

    companion object {
        val defaultGamesDTO
            get() = GamesDTO(
                games = listOf(defaultGameDTO),
                totalCount = 1
            )
    }
}
