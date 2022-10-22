package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.http.controllers.games.models.games.matchmake.MatchmakeOutputModel
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO
import pt.isel.daw.battleships.service.games.dtos.game.GameStateDTO
import pt.isel.daw.battleships.service.games.dtos.game.MatchmakeResponseDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchmakeOutputModelTests {

    @Test
    fun `MatchmakeModel creation is successful`() {
        MatchmakeOutputModel(
            matchmakeResponseDTO = MatchmakeResponseDTO(
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
        val matchmakeResponseDTO = MatchmakeResponseDTO(
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
        val matchmakeOutputModel = MatchmakeOutputModel(matchmakeResponseDTO)

        assertEquals(matchmakeResponseDTO.wasCreated, matchmakeOutputModel.wasCreated)
    }
}
