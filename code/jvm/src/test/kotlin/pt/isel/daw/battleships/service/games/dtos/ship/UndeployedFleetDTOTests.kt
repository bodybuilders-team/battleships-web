package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.ship.UndeployedShipDTOTests.Companion.defaultUndeployedShipDTO
import kotlin.test.Test

class UndeployedFleetDTOTests {

    @Test
    fun `UndeployedFleetDTO creation is successful`() {
        UndeployedFleetDTO(
            ships = listOf(defaultUndeployedShipDTO)
        )
    }

    companion object {
        val defaultUndeployedFleetDTO
            get() = UndeployedFleetDTO(
                ships = listOf(defaultUndeployedShipDTO)
            )
    }
}
