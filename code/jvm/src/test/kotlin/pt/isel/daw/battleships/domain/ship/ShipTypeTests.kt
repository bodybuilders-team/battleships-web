package pt.isel.daw.battleships.domain.ship

import kotlin.test.Test

class ShipTypeTests {

    @Test
    fun `ShipType creation is successful`() {
        ShipType(
            shipName = "Carrier",
            size = 5,
            quantity = 1,
            points = 5
        )
    }

    companion object {
        val defaultShipType
            get() = ShipType(
                shipName = "Carrier",
                size = 5,
                quantity = 1,
                points = 5
            )
    }
}
