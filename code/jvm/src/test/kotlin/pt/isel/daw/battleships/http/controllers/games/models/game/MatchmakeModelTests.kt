package pt.isel.daw.battleships.http.controllers.games.models.game

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
            matchmakeDTO = MatchmakeDTO(
                game = GameDTO(
                    id = 1,
                    name = "Game 1",
                    creator = "Player 1",
                    config = GameConfigDTO(1, 2, 3, 4, listOf()),
                    state = GameStateDTO("WAITING_FOR_PLAYERS", 1L, 1, null, null),
                    players = listOf()
                ),
                wasCreated = true
            )
        )
    }

    @Test
    fun `MatchmakeModel from MatchmakeDTO conversion is successful`() {
        val matchmakeDTO = MatchmakeDTO(
            game = GameDTO(
                id = 1,
                name = "Game 1",
                creator = "Player 1",
                config = GameConfigDTO(1, 2, 3, 4, listOf()),
                state = GameStateDTO("WAITING_FOR_PLAYERS", 1L, 1, null, null),
                players = listOf()
            ),
            wasCreated = true
        )
        val matchmakeModel = MatchmakeModel(matchmakeDTO)

        assertEquals(matchmakeDTO.wasCreated, matchmakeModel.wasCreated)
    }
}
