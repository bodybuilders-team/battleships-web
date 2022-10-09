package pt.isel.daw.battleships.services.games.dtos.game

import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameConfigDTOTests {
    @Test
    fun `GameConfigDTO creation is successful`() {
        GameConfigDTO(1, 2, 3, 4, listOf())
    }

    @Test
    fun `GameConfigDTO from GameConfig conversion is successful`() {
        val gameConfig = GameConfig(1, 2, 3, 4, listOf())
        val gameConfigDTO = GameConfigDTO(gameConfig)

        assertEquals(gameConfig.gridSize, gameConfigDTO.gridSize)
        assertEquals(gameConfig.maxTimePerShot, gameConfigDTO.maxTimePerShot)
        assertEquals(gameConfig.shotsPerRound, gameConfigDTO.shotsPerRound)
        assertEquals(gameConfig.maxTimeForLayoutPhase, gameConfigDTO.maxTimeForLayoutPhase)
        assertEquals(gameConfig.shipTypes.map { ShipTypeDTO(it) }, gameConfigDTO.shipTypes)
    }

    @Test
    fun `GameConfigDTO to GameConfig conversion is successful`() {
        val gameConfigDTO = GameConfigDTO(1, 2, 3, 4, listOf())
        val gameConfig = gameConfigDTO.toGameConfig()

        assertEquals(gameConfigDTO.gridSize, gameConfig.gridSize)
        assertEquals(gameConfigDTO.maxTimePerShot, gameConfig.maxTimePerShot)
        assertEquals(gameConfigDTO.shotsPerRound, gameConfig.shotsPerRound)
        assertEquals(gameConfigDTO.maxTimeForLayoutPhase, gameConfig.maxTimeForLayoutPhase)
        assertEquals(gameConfigDTO.shipTypes.map { it.toShipType() }, gameConfig.shipTypes)
    }
}
