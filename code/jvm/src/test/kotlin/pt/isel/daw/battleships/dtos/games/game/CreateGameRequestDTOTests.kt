package pt.isel.daw.battleships.dtos.games.game

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
