package pt.isel.daw.battleships.service.games.dtos.game

import kotlin.test.Test

class MatchmakeResponseDTOTests {

    @Test
    fun `MatchmakeDTO creation is successful`() {
        MatchmakeResponseDTO(
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
