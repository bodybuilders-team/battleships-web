package pt.isel.daw.battleships.service.games.dtos

import pt.isel.daw.battleships.domain.PlayerTests.Companion.defaultPlayer
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerDTOTests {

    @Test
    fun `PlayerDTO creation is successful`() {
        PlayerDTO(username = "username", points = 0)
    }

    @Test
    fun `PlayerDTO from Player conversion is successful`() {
        val player = defaultPlayer

        val playerDTO = PlayerDTO(player)

        assertEquals(player.user.username, playerDTO.username)
        assertEquals(player.points, playerDTO.points)
    }

    companion object {
        val defaultPlayerDTO
            get() = PlayerDTO(username = "username", points = 0)
    }
}
