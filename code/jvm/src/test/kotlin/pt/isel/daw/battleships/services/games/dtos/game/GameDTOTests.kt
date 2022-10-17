package pt.isel.daw.battleships.services.games.dtos.game

import kotlin.test.Test

class GameDTOTests {

    @Test
    fun `GameDTO creation is successful`() {
        GameDTO(
            id = 1,
            name = "name",
            creator = "creator",
            config = GameConfigDTO(
                gridSize = 1,
                maxTimeForLayoutPhase = 2,
                shotsPerRound = 3,
                maxTimePerRound = 4,
                shipTypes = listOf()
            ),
            state = GameStateDTO(
                phase = "WAITING_FOR_PLAYERS",
                phaseEndTime = 1L,
                round = 1,
                turn = null,
                winner = null
            ),
            players = listOf()
        )
    }

    @Test
    fun `GameDTO from Game conversion is successful`() {
        /*val game = Game(
            "name",
            User("Player 1", "haram@email.com", "1234", 0),
            GameConfig(1, 2, 3, 4, listOf()),
            GameState(GameState.GamePhase.WAITING_FOR_PLAYERS, 1, null, null)
        )
        val gameDTO = GameDTO(game) // ID NOT CREATED YET

        assertEquals(game.id, gameDTO.id)
        assertEquals(game.name, gameDTO.name)
        assertEquals(game.creator.username, gameDTO.creator)
        assertEquals(game.config, gameDTO.config.toGameConfig())
        assertEquals(GameStateDTO(game.state), gameDTO.state)
        assertEquals(game.players.map { PlayerDTO(it) }, gameDTO.players)*/
    }
}
