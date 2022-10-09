package pt.isel.daw.battleships.controllers.games.models.game.createGame

import pt.isel.daw.battleships.controllers.games.models.game.GameConfigModel
import pt.isel.daw.battleships.controllers.games.models.ship.ShipTypeModel
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateGameInputModelTests {
    @Test
    fun `CreateGameInputModel creation is successful`() {
        CreateGameInputModel("Game 1", GameConfigModel(1, 2, 3, 4, listOf()))
    }

    @Test
    fun `CreateGameInputModel to CreateGameRequestDTO conversion is successful`() {
        val shipType = ShipTypeModel("shipName", 1, 2, 3)
        val gameConfig = GameConfigModel(1, 2, 3, 4, listOf(shipType))
        val createGameInputModel = CreateGameInputModel("gameName", gameConfig)
        val createGameRequestDTO = createGameInputModel.toCreateGameRequestDTO()

        assertEquals(createGameRequestDTO.name, createGameInputModel.name)
        assertEquals(createGameRequestDTO.config, createGameInputModel.config.toGameConfigDTO())
    }
}
