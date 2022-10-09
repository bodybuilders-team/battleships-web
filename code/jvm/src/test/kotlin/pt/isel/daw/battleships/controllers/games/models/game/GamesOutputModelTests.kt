package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.services.games.dtos.game.GamesDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GamesOutputModelTests {
    @Test
    fun `GamesOutputModel creation is successful`() {
        GamesOutputModel(
            listOf(
                GameModel(
                    1,
                    "game1",
                    "player1",
                    GameConfigModel(1, 2, 3, 4, listOf()),
                    GameStateModel("WAITING_FOR_PLAYERS", 1, null, null),
                    listOf()
                )
            )
        )
    }

    @Test
    fun `GamesOutputModel creation is successful with empty list`() {
        GamesOutputModel(listOf())
    }

    @Test
    fun `GamesOutputModel from GamesDTO conversion is successful`() {
        val gamesDTO = GamesDTO(
            listOf(
                GameDTO(
                    1,
                    "game1",
                    "player1",
                    GameConfigDTO(1, 2, 3, 4, listOf()),
                    GameStateDTO("WAITING_FOR_PLAYERS", 1, null, null),
                    listOf()
                )
            )
        )
        val gamesOutputModel = GamesOutputModel(gamesDTO)

        assertEquals(gamesDTO.games.map { GameModel(it) }, gamesOutputModel.games)
    }
}
