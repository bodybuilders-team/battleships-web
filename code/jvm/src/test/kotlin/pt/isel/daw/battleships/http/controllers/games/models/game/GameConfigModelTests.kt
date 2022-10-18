package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.GameConfigDTO
import pt.isel.daw.battleships.dtos.games.ship.ShipTypeDTO
import pt.isel.daw.battleships.http.controllers.games.models.ship.ShipTypeModel
import kotlin.test.Test
import kotlin.test.assertEquals

class GameConfigModelTests {

    @Test
    fun `GameConfigModel creation is successful`() {
        GameConfigModel(
            gridSize = 1,
            maxTimeForLayoutPhase = 2,
            shotsPerRound = 3,
            maxTimePerShot = 4,
            shipTypes = listOf()
        )
    }

    @Test
    fun `GameConfigModel from GameConfigDTO conversion is successful`() {
        val shipTypeDTO = ShipTypeDTO("shipName", 2, 1, 3)
        val gameConfigDTO = GameConfigDTO(
            gridSize = 1,
            maxTimeForLayoutPhase = 2,
            shotsPerRound = 3,
            maxTimePerRound = 4,
            shipTypes = listOf(shipTypeDTO)
        )
        val gameConfig = GameConfigModel(gameConfigDTO)

        assertEquals(gameConfigDTO.gridSize, gameConfig.gridSize)
        assertEquals(gameConfigDTO.maxTimeForLayoutPhase, gameConfig.maxTimeForLayoutPhase)
        assertEquals(gameConfigDTO.shotsPerRound, gameConfig.shotsPerRound)
        assertEquals(gameConfigDTO.maxTimePerRound, gameConfig.maxTimePerShot)
        assertEquals(gameConfigDTO.shipTypes, gameConfig.shipTypes.map { it.toShipTypeDTO() })
    }

    @Test
    fun `GameConfigModel to GameConfigDTO conversion is successful`() {
        val shipType = ShipTypeModel("shipName", 1, 2, 3)
        val gameConfigModel = GameConfigModel(
            gridSize = 1,
            maxTimeForLayoutPhase = 2,
            shotsPerRound = 3,
            maxTimePerShot = 4,
            shipTypes = listOf(shipType)
        )
        val gameConfigDTO = gameConfigModel.toGameConfigDTO()

        assertEquals(gameConfigModel.gridSize, gameConfigDTO.gridSize)
        assertEquals(gameConfigModel.maxTimeForLayoutPhase, gameConfigDTO.maxTimeForLayoutPhase)
        assertEquals(gameConfigModel.shotsPerRound, gameConfigDTO.shotsPerRound)
        assertEquals(gameConfigModel.maxTimePerShot, gameConfigDTO.maxTimePerRound)
        assertEquals(gameConfigModel.shipTypes.map { it.toShipTypeDTO() }, gameConfigDTO.shipTypes)
    }

    @Test
    fun `GameConfigModel with invalid grid size throws`() {
        // TODO: validation with javax constraints in this test environment
    }
}