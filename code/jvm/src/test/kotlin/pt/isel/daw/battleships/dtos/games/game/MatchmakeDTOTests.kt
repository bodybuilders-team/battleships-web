package pt.isel.daw.battleships.dtos.games.game

import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import kotlin.test.Test

class MatchmakeDTOTests {

    @Test
    fun `MatchmakeDTO creation is successful`() {
        MatchmakeDTO(
            game = GameDTO(
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
            ),
            wasCreated = true
        )
    }
}
