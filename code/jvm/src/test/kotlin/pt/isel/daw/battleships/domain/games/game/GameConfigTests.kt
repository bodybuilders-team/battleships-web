package pt.isel.daw.battleships.domain.games.game

import pt.isel.daw.battleships.domain.exceptions.InvalidGameConfigException
import pt.isel.daw.battleships.domain.games.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.test.Test
import kotlin.test.assertFailsWith

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

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if grid size is below the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 0,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if grid size is above the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 20,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if max time per round is below the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 0,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if max time per round is above the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 0,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if max time for layout phase is below the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 0,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if max time for layout phase is above the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 150,
                shotsPerRound = 4,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if shots per round is below the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 0,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if shots per round is above the valid range`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 10,
                shipTypes = listOf(defaultShipType)
            )
        }
    }

    @Test
    fun `GameConfig creation throws InvalidGameConfigException if ship types is empty`() {
        assertFailsWith<InvalidGameConfigException> {
            GameConfig(
                gridSize = 10,
                maxTimePerRound = 60,
                maxTimeForLayoutPhase = 60,
                shotsPerRound = 4,
                shipTypes = emptyList()
            )
        }
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
