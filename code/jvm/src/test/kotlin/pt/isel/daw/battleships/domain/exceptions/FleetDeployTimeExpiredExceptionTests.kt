package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class FleetDeployTimeExpiredExceptionTests {

    @Test
    fun `FleetDeployTimeExpiredException creation is successful`() {
        FleetDeployTimeExpiredException("Test")
    }

    @Test
    fun `FleetDeployTimeExpiredException thrown successfully`() {
        assertFailsWith<FleetDeployTimeExpiredException> {
            throw FleetDeployTimeExpiredException("Test")
        }
    }
}