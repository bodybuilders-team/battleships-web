package pt.isel.daw.battleships.http.controllers.games.models.games

import pt.isel.daw.battleships.http.controllers.games.models.ShipTypeModelTests.Companion.defaultShipTypeModel
import pt.isel.daw.battleships.service.games.dtos.game.GameConfigDTOTests.Companion.defaultGameConfigDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GameConfigModelTests {

    @Test
    fun `GameConfigModel creation is successful`() {
        GameConfigModel(
            gridSize = 1,
            maxTimeForLayoutPhase = 2,
            shotsPerRound = 3,
            maxTimePerRound = 4,
            shipTypes = listOf(defaultShipTypeModel)
        )
    }

    @Test
    fun `GameConfigModel from GameConfigDTO conversion is successful`() {
        val gameConfigDTO = defaultGameConfigDTO

        val gameConfig = GameConfigModel(gameConfigDTO)

        assertEquals(gameConfigDTO.gridSize, gameConfig.gridSize)
        assertEquals(gameConfigDTO.maxTimeForLayoutPhase, gameConfig.maxTimeForLayoutPhase)
        assertEquals(gameConfigDTO.shotsPerRound, gameConfig.shotsPerRound)
        assertEquals(gameConfigDTO.maxTimePerRound, gameConfig.maxTimePerRound)
        assertEquals(gameConfigDTO.shipTypes, gameConfig.shipTypes.map { it.toShipTypeDTO() })
    }

    @Test
    fun `GameConfigModel to GameConfigDTO conversion is successful`() {
        val gameConfigModel = defaultGameConfigModel

        val gameConfigDTO = gameConfigModel.toGameConfigDTO()

        assertEquals(gameConfigModel.gridSize, gameConfigDTO.gridSize)
        assertEquals(gameConfigModel.maxTimeForLayoutPhase, gameConfigDTO.maxTimeForLayoutPhase)
        assertEquals(gameConfigModel.shotsPerRound, gameConfigDTO.shotsPerRound)
        assertEquals(gameConfigModel.maxTimePerRound, gameConfigDTO.maxTimePerRound)
        assertEquals(gameConfigModel.shipTypes.map { it.toShipTypeDTO() }, gameConfigDTO.shipTypes)
    }

    companion object {
        val defaultGameConfigModel
            get() = GameConfigModel(
                gridSize = 1,
                maxTimeForLayoutPhase = 2,
                shotsPerRound = 3,
                maxTimePerRound = 4,
                shipTypes = listOf(defaultShipTypeModel)
            )
    }
}
