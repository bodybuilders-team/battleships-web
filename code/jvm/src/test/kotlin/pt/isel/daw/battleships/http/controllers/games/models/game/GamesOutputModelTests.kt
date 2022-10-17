package pt.isel.daw.battleships.http.controllers.games.models.game

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
            gamesDTO = GamesDTO(
                games = listOf(
                    GameDTO(
                        id = 1,
                        name = "game1",
                        creator = "player1",
                        config = GameConfigDTO(1, 2, 3, 4, listOf()),
                        state = GameStateDTO("WAITING_FOR_PLAYERS", 1L, 1, null, null),
                        players = listOf()
                    )
                ),
                totalCount = 1
            )
        )
    }

    @Test
    fun `GamesOutputModel creation is successful with empty list`() {
        GamesOutputModel(
            gamesDTO = GamesDTO(
                games = emptyList(),
                totalCount = 0
            )
        )
    }

    @Test
    fun `GamesOutputModel from GamesDTO conversion is successful`() {
        val gamesDTO = GamesDTO(
            games = listOf(
                GameDTO(
                    id = 1,
                    name = "game1",
                    creator = "player1",
                    config = GameConfigDTO(1, 2, 3, 4, listOf()),
                    state = GameStateDTO("WAITING_FOR_PLAYERS", 1L, 1, null, null),
                    players = listOf()
                )
            ),
            totalCount = 1
        )
        val gamesOutputModel = GamesOutputModel(gamesDTO)

        assertEquals(gamesDTO.games.size, gamesOutputModel.totalCount)
    }
}
