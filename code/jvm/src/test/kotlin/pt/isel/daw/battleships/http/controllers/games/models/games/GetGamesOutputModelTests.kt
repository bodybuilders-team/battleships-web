package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.http.controllers.games.models.games.getGames.GetGamesOutputModel
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.service.games.dtos.game.GamesDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetGamesOutputModelTests {

    @Test
    fun `GamesOutputModel creation is successful`() {
        GetGamesOutputModel(
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
        GetGamesOutputModel(
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
        val getGamesOutputModel = GetGamesOutputModel(gamesDTO)

        assertEquals(gamesDTO.games.size, getGamesOutputModel.totalCount)
    }
}
