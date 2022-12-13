package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTOTests.Companion.defaultGameStateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameStateModelTests {

    @Test
    fun `GameStateModel creation is successful`() {
        GameStateModel(
            phase = "WAITING_FOR_PLAYERS",
            phaseEndTime = 1L,
            round = 1,
            turn = null,
            winner = null,
            endCause = null
        )
    }

    @Test
    fun `GameStateModel from GameStateDTO conversion is successful`() {
        val gameStateDTO = defaultGameStateDTO

        val gameStateModel = GameStateModel(gameStateDTO)

        assertEquals(gameStateDTO.phase, gameStateModel.phase)
        assertEquals(gameStateDTO.round, gameStateModel.round)
        assertEquals(gameStateDTO.turn, gameStateModel.turn)
        assertEquals(gameStateDTO.winner, gameStateModel.winner)
    }

    companion object {
        val defaultGameStateModel
            get() = GameStateModel(
                phase = "WAITING_FOR_PLAYERS",
                phaseEndTime = 1L,
                round = 1,
                turn = null,
                winner = null,
                endCause = null
            )
    }
}
