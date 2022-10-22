package pt.isel.daw.battleships.dtos.games.game

import pt.isel.daw.battleships.services.games.dtos.game.CreateGameInputDTO
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import kotlin.test.Test

class CreateGameRequestDTOTests {

    @Test
    fun `CreateGameRequestDTO creation is successful`() {
        CreateGameInputDTO(
            name = "name",
            config = GameConfigDTO(
                gridSize = 1,
                maxTimeForLayoutPhase = 2,
                shotsPerRound = 3,
                maxTimePerRound = 4,
                shipTypes = listOf()
            )
        )
    }
}
