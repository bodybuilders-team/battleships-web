package pt.isel.daw.battleships.http.controllers.games.models.games.getGames

import pt.isel.daw.battleships.service.games.dtos.game.GameDTOTests.Companion.defaultGameDTO
import pt.isel.daw.battleships.service.games.dtos.game.GamesDTO
import pt.isel.daw.battleships.service.games.dtos.game.GamesDTOTests.Companion.defaultGamesDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetGamesOutputModelTests {

    @Test
    fun `GetGamesOutputModel creation is successful`() {
        GetGamesOutputModel(
            gamesDTO = GamesDTO(
                games = listOf(defaultGameDTO),
                totalCount = 1
            )
        )
    }

    @Test
    fun `GetGamesOutputModel from GamesDTO conversion is successful`() {
        val gamesDTO = defaultGamesDTO

        val getGamesOutputModel = GetGamesOutputModel(gamesDTO)

        assertEquals(gamesDTO.games.size, getGamesOutputModel.totalCount)
    }

    companion object {
        val defaultGetGamesOutputModel
            get() = GetGamesOutputModel(
                gamesDTO = GamesDTO(
                    games = listOf(defaultGameDTO),
                    totalCount = 1
                )
            )
    }
}
