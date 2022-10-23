package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.http.controllers.games.models.games.getGame.GetGameOutputModel
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetGameOutputModelTests {

    @Test
    fun `GameModel creation is successful`() {
        GetGameOutputModel(
            id = 1,
            name = "Game 1",
            creator = "Player 1",
            config = GameConfigModel(
                gridSize = 1,
                maxTimeForLayoutPhase = 2,
                shotsPerRound = 3,
                maxTimePerRound = 4,
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
        val getGameOutputModel = GetGameOutputModel(gameDTO)

        assertEquals(gameDTO.id, getGameOutputModel.id)
        assertEquals(gameDTO.name, getGameOutputModel.name)
        assertEquals(gameDTO.creator, getGameOutputModel.creator)
        assertEquals(GameConfigModel(gameDTO.config), getGameOutputModel.config)
        assertEquals(GameStateModel(gameDTO.state), getGameOutputModel.state)
        assertEquals(gameDTO.players.map { PlayerModel(it) }, getGameOutputModel.players)
    }
}
