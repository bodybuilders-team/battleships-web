package pt.isel.daw.battleships.http.controllers.games.models

import pt.isel.daw.battleships.http.controllers.games.models.games.PlayerModel
import pt.isel.daw.battleships.service.games.dtos.PlayerDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerModelTests {

    @Test
    fun `PlayerModel creation is successful`() {
        PlayerModel(
            username = "Player 1",
            points = 1
        )
    }

    @Test
    fun `PlayerModel from PlayerDTO conversion is successful`() {
        val playerDTO = PlayerDTO(
            username = "Player 1",
            points = 1
        )
        val playerModel = PlayerModel(playerDTO)

        assertEquals(playerDTO.username, playerModel.username)
        assertEquals(playerDTO.points, playerModel.points)
    }
}
