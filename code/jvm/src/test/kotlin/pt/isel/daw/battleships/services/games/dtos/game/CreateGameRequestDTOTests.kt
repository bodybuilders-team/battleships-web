package pt.isel.daw.battleships.services.games.dtos.game

import kotlin.test.Test

class CreateGameRequestDTOTests {
    @Test
    fun `CreateGameRequestDTO creation is successful`() {
        CreateGameRequestDTO(
            "name",
            GameConfigDTO(1, 2, 3, 4, listOf())
        )
    }
}
