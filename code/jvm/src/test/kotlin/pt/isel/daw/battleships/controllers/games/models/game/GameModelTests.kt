package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.games.models.PlayerModel
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameModelTests {
    @Test
    fun `GameModel creation is successful`() {
        GameModel(
            1,
            "Game 1",
            "Player 1",
            GameConfigModel(1, 2, 3, 4, listOf()),
            GameStateModel("WAITING_FOR_PLAYERS", 1, null, null),
            listOf()
        )
    }

    @Test
    fun `GameModel from GameDTO conversion is successful`() {
        val gameDTO = GameDTO(
            1,
            "Game 1",
            "Player 1",
            GameConfigDTO(1, 2, 3, 4, listOf()),
            GameStateDTO("WAITING_FOR_PLAYERS", 1, null, null),
            listOf()
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
