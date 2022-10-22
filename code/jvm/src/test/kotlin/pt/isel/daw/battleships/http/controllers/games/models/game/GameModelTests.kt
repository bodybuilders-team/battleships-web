package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.dtos.games.game.GameDTO
import pt.isel.daw.battleships.dtos.games.game.GameStateDTO
import pt.isel.daw.battleships.http.controllers.games.models.PlayerModel
import kotlin.test.Test
import kotlin.test.assertEquals

class GameModelTests {

    @Test
    fun `GameModel creation is successful`() {
        GameModel(
            id = 1,
            name = "Game 1",
            creator = "Player 1",
            config = GameConfigModel(
                gridSize = 1,
                maxTimeForLayoutPhase = 2,
                shotsPerRound = 3,
                maxTimePerShot = 4,
                shipTypes = listOf()
            ),
            state = GameStateModel(
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
    fun `GameModel from GameDTO conversion is successful`() {
        val gameDTO = GameDTO(
            id = 1,
            name = "Game 1",
            creator = "Player 1",
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
        val gameModel = GameModel(gameDTO)

        assertEquals(gameDTO.id, gameModel.id)
        assertEquals(gameDTO.name, gameModel.name)
        assertEquals(gameDTO.creator, gameModel.creator)
        assertEquals(GameConfigModel(gameDTO.config), gameModel.config)
        assertEquals(GameStateModel(gameDTO.state), gameModel.state)
        assertEquals(gameDTO.players.map { PlayerModel(it) }, gameModel.players)
    }
}
