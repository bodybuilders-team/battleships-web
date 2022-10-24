package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class FleetAlreadyDeployedExceptionTests {

    @Test
    fun `FleetAlreadyDeployedException creation is successful`() {
        FleetAlreadyDeployedException("Test")
    }

    @Test
    fun `FleetAlreadyDeployedException thrown successfully`() {
        assertFailsWith<FleetAlreadyDeployedException> {
            throw FleetAlreadyDeployedException("Test")
        }
    }
}