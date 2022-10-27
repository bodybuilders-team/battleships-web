package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.domain.games.game.GameTests.Companion.defaultGame
import pt.isel.daw.battleships.service.games.dtos.PlayerDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTOTests.Companion.defaultGameConfigDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTOTests.Companion.defaultGameStateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameDTOTests {

    @Test
    fun `GameDTO creation is successful`() {
        GameDTO(
            id = 1,
            name = "name",
            creator = "creator",
            config = defaultGameConfigDTO,
            state = defaultGameStateDTO,
            players = listOf()
        )
    }

    @Test
    fun `GameDTO from Game conversion is successful`() {
        val game = defaultGame.also { it.id = 1 }

        val gameDTO = GameDTO(game)

        assertEquals(game.id, gameDTO.id)
        assertEquals(game.name, gameDTO.name)
        assertEquals(game.creator.username, gameDTO.creator)
        assertEquals(GameConfigDTO(game.config), gameDTO.config)
        assertEquals(GameStateDTO(game.state), gameDTO.state)
        assertEquals(game.players.map { PlayerDTO(it) }, gameDTO.players)
    }

    companion object {
        val defaultGameDTO
            get() = GameDTO(
                id = 1,
                name = "name",
                creator = "creator",
                config = defaultGameConfigDTO,
                state = defaultGameStateDTO,
                players = listOf()
            )
    }
}
