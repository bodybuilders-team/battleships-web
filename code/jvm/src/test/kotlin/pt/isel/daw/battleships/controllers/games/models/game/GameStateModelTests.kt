package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameStateModelTests {
    @Test
    fun `GameStateModel creation is successful`() {
        GameStateModel("WAITING_FOR_PLAYERS", 1, null, null)
    }

    @Test
    fun `GameStateModel from GameStateDTO conversion is successful`() {
        val gameStateDTO = GameStateDTO("WAITING_FOR_PLAYERS", 1, null, null)
        val gameStateModel = GameStateModel(gameStateDTO)

        assertEquals(gameStateDTO.phase, gameStateModel.phase)
        assertEquals(gameStateDTO.round, gameStateModel.round)
        assertEquals(gameStateDTO.turn, gameStateModel.turn)
        assertEquals(gameStateDTO.winner, gameStateModel.winner)
    }
}