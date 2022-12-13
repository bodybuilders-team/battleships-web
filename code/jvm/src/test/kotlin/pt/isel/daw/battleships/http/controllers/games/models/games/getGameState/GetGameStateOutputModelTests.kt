package pt.isel.daw.battleships.http.controllers.games.models.games.getGameState

import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTOTests.Companion.defaultGameStateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetGameStateOutputModelTests {

    @Test
    fun `GetGameStateOutputModel creation is successful`() {
        GetGameStateOutputModel(
            phase = "WAITING_FOR_PLAYERS",
            phaseEndTime = 1L,
            round = 1,
            turn = null,
            winner = null,
            endCause = null
        )
    }

    @Test
    fun `GetGameStateOutputModel from GameStateDTO conversion is successful`() {
        val gameStateDTO = defaultGameStateDTO

        val getGameStateOutputModel = GetGameStateOutputModel(gameStateDTO)

        assertEquals(gameStateDTO.phase, getGameStateOutputModel.phase)
        assertEquals(gameStateDTO.round, getGameStateOutputModel.round)
        assertEquals(gameStateDTO.turn, getGameStateOutputModel.turn)
        assertEquals(gameStateDTO.winner, getGameStateOutputModel.winner)
    }

    companion object {
        val defaultGetGameStateOutputModel
            get() = GetGameStateOutputModel(
                phase = "WAITING_FOR_PLAYERS",
                phaseEndTime = 1L,
                round = 1,
                turn = null,
                winner = null,
                endCause = null
            )
    }
}
