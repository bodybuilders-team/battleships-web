package pt.isel.daw.battleships.services.games.dtos.game

import pt.isel.daw.battleships.database.model.game.GameState
import kotlin.test.Test
import kotlin.test.assertEquals

class GameStateDTOTests {
    @Test
    fun `GameStateDTO creation is successful`() {
        GameStateDTO("phase", 1, null, null)
    }

    @Test
    fun `GameStateDTO from GameState conversion is successful`() {
        val gameState = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS, 1, null, null)
        val gameStateDTO = GameStateDTO(gameState)

        assertEquals(gameState.phase.name, gameStateDTO.phase)
        assertEquals(gameState.round, gameStateDTO.round)
        assertEquals(gameState.turn?.user?.username, gameStateDTO.turn)
        assertEquals(gameState.winner?.user?.username, gameStateDTO.winner)
    }
}
