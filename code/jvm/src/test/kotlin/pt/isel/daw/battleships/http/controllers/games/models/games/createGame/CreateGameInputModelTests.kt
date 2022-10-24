package pt.isel.daw.battleships.http.controllers.games.models.games.createGame

import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModelTests.Companion.defaultGameConfigModel
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateGameInputModelTests {

    @Test
    fun `CreateGameInputModel creation is successful`() {
        CreateGameInputModel(
            name = "Game 1",
            config = defaultGameConfigModel
        )
    }

    @Test
    fun `CreateGameInputModel to CreateGameRequestDTO conversion is successful`() {
        val createGameInputModel = defaultCreateGameInputModel

        val createGameRequestDTO = createGameInputModel.toCreateGameRequestDTO()

        assertEquals(createGameInputModel.name, createGameRequestDTO.name)
        assertEquals(createGameInputModel.config.toGameConfigDTO(), createGameRequestDTO.config)
    }

    companion object {
        val defaultCreateGameInputModel
            get() = CreateGameInputModel(
                name = "Game 1",
                config = defaultGameConfigModel
            )
    }
}
