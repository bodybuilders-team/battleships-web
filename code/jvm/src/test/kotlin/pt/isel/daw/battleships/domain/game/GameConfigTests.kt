package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.test.Test

class GameConfigTests {

    @Test
    fun `GameConfig creation is successful`() {
        GameConfig(
            gridSize = 10,
            maxTimePerRound = 60,
            maxTimeForLayoutPhase = 60,
            shotsPerRound = 4,
            shipTypes = listOf(defaultShipType)
        )
    }

    companion object {
        val defaultGameConfig
            get() = GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
    }
}
