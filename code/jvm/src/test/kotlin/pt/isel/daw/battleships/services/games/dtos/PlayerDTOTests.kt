package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerDTOTests {

    @Test
    fun `PlayerDTO creation is successful`() {
        PlayerDTO(username = "username", points = 0)
    }

    @Test
    fun `PlayerDTO from Player conversion is successful`() {
        val user = User(username = "username", email = "email", hashedPassword = "password", points = 0)
        val player = Player(
            game = Game(
                name = "game",
                creator = user,
                config = GameConfig(
                    gridSize = 1,
                    maxTimePerRound = 2,
                    shotsPerRound = 3,
                    maxTimeForLayoutPhase = 4,
                    shipTypes = listOf()
                ),
                state = GameState(
                    phase = GameState.GamePhase.WAITING_FOR_PLAYERS,
                    phaseEndTime = Timestamp(Instant.now().epochSecond),
                    round = 1,
                    turn = null,
                    winner = null
                )
            ),
            user = user,
            points = 0
        )
        val playerDTO = PlayerDTO(player)

        assertEquals(player.user.username, playerDTO.username)
        assertEquals(player.points, playerDTO.points)
    }
}
