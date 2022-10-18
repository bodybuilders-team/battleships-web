package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.GameStateDTO
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
            winner = null
        )
    }

    @Test
    fun `GameStateModel from GameStateDTO conversion is successful`() {
        val gameStateDTO = GameStateDTO(
            phase = "WAITING_FOR_PLAYERS",
            phaseEndTime = 1L,
            round = 1,
            turn = null,
            winner = null
        )
        val gameStateModel = GameStateModel(gameStateDTO)

        assertEquals(gameStateDTO.phase, gameStateModel.phase)
        assertEquals(gameStateDTO.round, gameStateModel.round)
        assertEquals(gameStateDTO.turn, gameStateModel.turn)
        assertEquals(gameStateDTO.winner, gameStateModel.winner)
    }
}
