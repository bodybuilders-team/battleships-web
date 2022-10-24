package pt.isel.daw.battleships.service.games.dtos.game

import pt.isel.daw.battleships.domain.game.GameConfigTests.Companion.defaultGameConfig
import pt.isel.daw.battleships.service.games.dtos.ship.ShipTypeDTO
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
        val gameConfig = defaultGameConfig

        val gameConfigDTO = GameConfigDTO(gameConfig)

        assertEquals(gameConfig.gridSize, gameConfigDTO.gridSize)
        assertEquals(gameConfig.maxTimePerRound, gameConfigDTO.maxTimePerRound)
        assertEquals(gameConfig.shotsPerRound, gameConfigDTO.shotsPerRound)
        assertEquals(gameConfig.maxTimeForLayoutPhase, gameConfigDTO.maxTimeForLayoutPhase)
        assertEquals(gameConfig.shipTypes.map { ShipTypeDTO(it) }, gameConfigDTO.shipTypes)
    }

    @Test
    fun `GameConfigDTO to GameConfig conversion is successful`() {
        val gameConfigDTO = defaultGameConfigDTO

        val gameConfig = gameConfigDTO.toGameConfig()

        assertEquals(gameConfigDTO.gridSize, gameConfig.gridSize)
        assertEquals(gameConfigDTO.maxTimePerRound, gameConfig.maxTimePerRound)
        assertEquals(gameConfigDTO.shotsPerRound, gameConfig.shotsPerRound)
        assertEquals(gameConfigDTO.maxTimeForLayoutPhase, gameConfig.maxTimeForLayoutPhase)
        assertEquals(gameConfigDTO.shipTypes.map { it.toShipType() }, gameConfig.shipTypes)
    }

    companion object {
        val defaultGameConfigDTO
            get() = GameConfigDTO(
                gridSize = 7,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 3,
                maxTimePerRound = 60,
                shipTypes = listOf(
                    ShipTypeDTO("Carrier", 1, 5, 50)
                )
            )
    }
}
