package pt.isel.daw.battleships.services.games.dtos.game

import pt.isel.daw.battleships.dtos.games.game.GameConfigDTO
import pt.isel.daw.battleships.dtos.games.game.GameDTO
import pt.isel.daw.battleships.dtos.games.game.GameStateDTO
import pt.isel.daw.battleships.dtos.games.game.GamesDTO
import kotlin.test.Test

class GamesDTOTests {

    @Test
    fun `GamesDTO creation is successful`() {
        GamesDTO(
            games = listOf(
                GameDTO(
                    id = 1,
                    name = "name",
                    creator = "creator",
                    config = GameConfigDTO(
                        gridSize = 1,
                        maxTimeForLayoutPhase = 2,
                        shotsPerRound = 3,
                        maxTimePerRound = 4,
                        shipTypes = listOf()
                    ),
                    state = GameStateDTO(
                        phase = "WAITING_FOR_PLAYERS",
                        phaseEndTime = 1L,
                        round = 1,
                        turn = null,
                        winner = null
                    ),
                    players = listOf()
                )
            ),
            totalCount = 1
        )
    }
}
