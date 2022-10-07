package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.games.models.ship.ShipTypeModel
import pt.isel.daw.battleships.services.games.dtos.game.GameConfigDTO
import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameConfigModelTests {
    @Test
    fun `GameConfigModel creation is successful`() {
        GameConfigModel(1, 2, 3, 4, listOf())
    }

    @Test
    fun `GameConfigModel from GameConfigDTO conversion is successful`() {
        val shipTypeDTO = ShipTypeDTO("shipName", 1, 2, 3)
        val gameConfigDTO = GameConfigDTO(1, 2, 3, 4, listOf(shipTypeDTO))
        val gameConfig = GameConfigModel(gameConfigDTO)

        assertEquals(gameConfigDTO.gridSize, gameConfig.gridSize)
        assertEquals(gameConfigDTO.maxTimeForLayoutPhase, gameConfig.maxTimeForLayoutPhase)
        assertEquals(gameConfigDTO.shotsPerRound, gameConfig.shotsPerRound)
        assertEquals(gameConfigDTO.maxTimePerShot, gameConfig.maxTimePerShot)
        assertEquals(gameConfigDTO.shipTypes, gameConfig.shipTypes.map { it.toShipTypeDTO() })
    }

    @Test
    fun `GameConfigModel to GameConfigDTO conversion is successful`() {
        val shipType = ShipTypeModel("shipName", 1, 2, 3)
        val gameConfigModel = GameConfigModel(1, 2, 3, 4, listOf(shipType))
        val gameConfigDTO = gameConfigModel.toGameConfigDTO()

        assertEquals(gameConfigModel.gridSize, gameConfigDTO.gridSize)
        assertEquals(gameConfigModel.maxTimeForLayoutPhase, gameConfigDTO.maxTimeForLayoutPhase)
        assertEquals(gameConfigModel.shotsPerRound, gameConfigDTO.shotsPerRound)
        assertEquals(gameConfigModel.maxTimePerShot, gameConfigDTO.maxTimePerShot)
        assertEquals(gameConfigModel.shipTypes.map { it.toShipTypeDTO() }, gameConfigDTO.shipTypes)
    }

    @Test
    fun `GameConfigModel with invalid grid size throws`() {
        // TODO validation with javax constraints in this test environment
    }
}