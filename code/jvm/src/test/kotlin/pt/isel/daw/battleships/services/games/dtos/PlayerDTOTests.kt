package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerDTOTests {
    @Test
    fun `PlayerDTO creation is successful`() {
        PlayerDTO("username", 0)
    }

    @Test
    fun `PlayerDTO from Player conversion is successful`() {
        val user = User("username", "email", "password", 0)
        val player = Player(
            Game(
                "game",
                user,
                GameConfig(1, 2, 3, 4, listOf()),
                GameState(GameState.GamePhase.WAITING_FOR_PLAYERS, 1, null, null)
            ),
            user,
            0
        )
        val playerDTO = PlayerDTO(player)

        assertEquals(player.user.username, playerDTO.username)
        assertEquals(player.points, playerDTO.points)
    }
}
