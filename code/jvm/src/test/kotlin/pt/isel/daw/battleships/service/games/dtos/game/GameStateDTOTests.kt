package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.domain.game.GameState
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class GameStateDTOTests {

    @Test
    fun `GameStateDTO creation is successful`() {
        GameStateDTO(
            phase = "phase",
            phaseEndTime = 1L,
            round = 1,
            turn = null,
            winner = null
        )
    }

    @Test
    fun `GameStateDTO from GameState conversion is successful`() {
        val gameState = GameState(
            phase = GameState.GamePhase.WAITING_FOR_PLAYERS,
            phaseExpirationTime = Timestamp(Instant.now().epochSecond),
            round = 1,
            turn = null,
            winner = null
        )
        val gameStateDTO = GameStateDTO(gameState = gameState)

        assertEquals(gameState.phase.name, gameStateDTO.phase)
        assertEquals(gameState.round, gameStateDTO.round)
        assertEquals(gameState.turn?.user?.username, gameStateDTO.turn)
        assertEquals(gameState.winner?.user?.username, gameStateDTO.winner)
    }
}
