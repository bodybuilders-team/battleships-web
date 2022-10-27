package pt.isel.daw.battleships.domain.games.game

import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test

class GameStateTests {

    @Test
    fun `GameState creation is successful`() {
        GameState(
            GameState.GamePhase.WAITING_FOR_PLAYERS,
            Timestamp.from(Instant.now()),
            null,
            null
        )
    }

    companion object {
        val defaultGameState
            get() = GameState(
                GameState.GamePhase.WAITING_FOR_PLAYERS,
                Timestamp.from(Instant.now()),
                null,
                null
            )
    }
}
