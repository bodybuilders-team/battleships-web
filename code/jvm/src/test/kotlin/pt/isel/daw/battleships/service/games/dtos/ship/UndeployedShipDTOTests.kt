package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTOTests.Companion.defaultCoordinateDTO
import kotlin.test.Test

class UndeployedShipDTOTests {

    @Test
    fun `UndeployedShipDTO creation is successful`() {
        UndeployedShipDTO(
            type = "type",
            coordinate = defaultCoordinateDTO,
            orientation = "HORIZONTAL"
        )
    }

    companion object {
        val defaultUndeployedShipDTO
            get() = UndeployedShipDTO(
                type = "type",
                coordinate = defaultCoordinateDTO,
                orientation = "HORIZONTAL"
            )
    }
}
