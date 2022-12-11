package pt.isel.daw.battleships.http.controllers.games.models.games.matchmake

import pt.isel.daw.battleships.service.games.dtos.game.MatchmakeResponseDTOTests.Companion.defaultMatchmakeResponseDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchmakeOutputModelTests {

    @Test
    fun `MatchmakeOutputModel creation is successful`() {
        MatchmakeOutputModel(
            gameId = 1,
            wasCreated = true
        )
    }

    @Test
    fun `MatchmakeOutputModel from MatchmakeResponseDTO conversion is successful`() {
        val matchmakeResponseDTO = defaultMatchmakeResponseDTO

        val matchmakeOutputModel = MatchmakeOutputModel(matchmakeResponseDTO = matchmakeResponseDTO)

        assertEquals(matchmakeResponseDTO.wasCreated, matchmakeOutputModel.wasCreated)
    }

    companion object {
        val defaultMatchmakeOutputModel
            get() = MatchmakeOutputModel(
                gameId=1,
                wasCreated = true
            )
    }
}
