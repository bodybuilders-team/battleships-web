package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.ship.ShipType
import kotlin.test.Test

class GameConfigTests {

    @Test
    fun `GameConfig creation is successful`() {
        GameConfig(
            gridSize = 10,
            maxTimePerRound = 60,
            maxTimeForLayoutPhase = 60,
            shotsPerRound = 4,
            shipTypes = listOf(
                ShipType("Carrier", 1, 5, 50)
            )
        )
    }

    companion object {
        val defaultGameConfig
            get() = GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = listOf(
                    ShipType("Carrier", 1, 5, 50)
                )
            )
    }
}
