package pt.isel.daw.battleships.controllers.games.models

import pt.isel.daw.battleships.services.games.dtos.PlayerDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerModelTests {
    @Test
    fun `PlayerModel creation is successful`() {
        PlayerModel("Player 1", 1)
    }

    @Test
    fun `PlayerModel from PlayerDTO conversion is successful`() {
        val playerDTO = PlayerDTO("Player 1", 1)
        val playerModel = PlayerModel(playerDTO)

        assertEquals(playerDTO.username, playerModel.username)
        assertEquals(playerDTO.points, playerModel.points)
    }
}
