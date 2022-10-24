package pt.isel.daw.battleships.http.controllers.games.models.games.getGame

import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModel
import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModelTests.Companion.defaultGameConfigModel
import pt.isel.daw.battleships.http.controllers.games.models.games.GameStateModel
import pt.isel.daw.battleships.http.controllers.games.models.games.GameStateModelTests.Companion.defaultGameStateModel
import pt.isel.daw.battleships.http.controllers.games.models.games.PlayerModel
import pt.isel.daw.battleships.service.games.dtos.game.GameDTOTests.Companion.defaultGameDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GetGameOutputModelTests {

    @Test
    fun `GetGameOutputModel creation is successful`() {
        GetGameOutputModel(
            id = 1,
            name = "Game 1",
            creator = "Player 1",
            config = defaultGameConfigModel,
            state = defaultGameStateModel,
            players = listOf()
        )
    }

    @Test
    fun `GameModel from GameDTO conversion is successful`() {
        val gameDTO = defaultGameDTO

        val getGameOutputModel = GetGameOutputModel(gameDTO)

        assertEquals(gameDTO.id, getGameOutputModel.id)
        assertEquals(gameDTO.name, getGameOutputModel.name)
        assertEquals(gameDTO.creator, getGameOutputModel.creator)
        assertEquals(GameConfigModel(gameDTO.config), getGameOutputModel.config)
        assertEquals(GameStateModel(gameDTO.state), getGameOutputModel.state)
        assertEquals(gameDTO.players.map { PlayerModel(it) }, getGameOutputModel.players)
    }

    companion object {
        val defaultGetGameOutputModel
            get() = GetGameOutputModel(
                id = 1,
                name = "Game 1",
                creator = "Player 1",
                config = defaultGameConfigModel,
                state = defaultGameStateModel,
                players = listOf()
            )
    }
}
