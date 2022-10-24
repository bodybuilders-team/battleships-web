package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTOTests.Companion.defaultGameConfigDTO
import kotlin.test.Test

class CreateGameRequestDTOTests {

    @Test
    fun `CreateGameRequestDTO creation is successful`() {
        CreateGameRequestDTO(
            name = "name",
            config = defaultGameConfigDTO
        )
    }

    companion object {
        val defaultCreateGameRequestDTO
            get() = CreateGameRequestDTO(
                name = "name",
                config = defaultGameConfigDTO
            )
    }
}
