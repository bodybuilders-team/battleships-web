package pt.isel.daw.battleships.dtos.games.game

import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.dtos.games.ship.ShipTypeDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameConfigDTOTests {

    @Test
    fun `GameConfigDTO creation is successful`() {
        GameConfigDTO(
            gridSize = 1,
            maxTimeForLayoutPhase = 2,
            shotsPerRound = 3,
            maxTimePerRound = 4,
            shipTypes = listOf()
        )
    }

    @Test
    fun `GameConfigDTO from GameConfig conversion is successful`() {
        val gameConfig = GameConfig(
            gridSize = 1,
            maxTimePerRound = 2,
            shotsPerRound = 3,
            maxTimeForLayoutPhase = 4,
            shipTypes = listOf()
        )
        val gameConfigDTO = GameConfigDTO(gameConfig)

        assertEquals(gameConfig.gridSize, gameConfigDTO.gridSize)
        assertEquals(gameConfig.maxTimePerRound, gameConfigDTO.maxTimePerRound)
        assertEquals(gameConfig.shotsPerRound, gameConfigDTO.shotsPerRound)
        assertEquals(gameConfig.maxTimeForLayoutPhase, gameConfigDTO.maxTimeForLayoutPhase)
        assertEquals(gameConfig.shipTypes.map { ShipTypeDTO(it) }, gameConfigDTO.shipTypes)
    }

    @Test
    fun `GameConfigDTO to GameConfig conversion is successful`() {
        val gameConfigDTO = GameConfigDTO(
            gridSize = 1,
            maxTimeForLayoutPhase = 2,
            shotsPerRound = 3,
            maxTimePerRound = 4,
            shipTypes = listOf()
        )
        val gameConfig = gameConfigDTO.toGameConfig()

        assertEquals(gameConfigDTO.gridSize, gameConfig.gridSize)
        assertEquals(gameConfigDTO.maxTimePerRound, gameConfig.maxTimePerRound)
        assertEquals(gameConfigDTO.shotsPerRound, gameConfig.shotsPerRound)
        assertEquals(gameConfigDTO.maxTimeForLayoutPhase, gameConfig.maxTimeForLayoutPhase)
        assertEquals(gameConfigDTO.shipTypes.map { it.toShipType() }, gameConfig.shipTypes)
    }
}
