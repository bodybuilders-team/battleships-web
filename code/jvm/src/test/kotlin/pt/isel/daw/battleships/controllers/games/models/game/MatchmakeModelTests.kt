package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.services.games.dtos.game.MatchmakeDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchmakeModelTests {

    @Test
    fun `MatchmakeModel creation is successful`() {
        MatchmakeModel(
            GameModel(
                1,
                "game",
                "Player 1",
                GameConfigModel(1, 2, 3, 4, listOf()),
                GameStateModel("WAITING_FOR_PLAYERS", 1, null, null),
                listOf()
            ),
            true
        )
    }

    @Test
    fun `MatchmakeModel from MatchmakeDTO conversion is successful`() {
        val matchmakeDTO = MatchmakeDTO(
            GameDTO(
                1,
                "game",
                "Player 1",
                GameConfigDTO(1, 2, 3, 4, listOf()),
                GameStateDTO("WAITING_FOR_PLAYERS", 1, null, null),
                listOf()
            ),
            true
        )
        val matchmakeModel = MatchmakeModel(matchmakeDTO)

        assertEquals(GameModel(matchmakeDTO.game), matchmakeModel.game)
        assertEquals(matchmakeDTO.wasCreated, matchmakeModel.wasCreated)
    }
}
