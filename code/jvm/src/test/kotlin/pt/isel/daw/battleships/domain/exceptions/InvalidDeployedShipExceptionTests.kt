package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidDeployedShipExceptionTests {

    @Test
    fun `InvalidDeployedShipException creation is successful`() {
        InvalidDeployedShipException("Test")
    }

    @Test
    fun `InvalidDeployedShipException thrown successfully`() {
        assertFailsWith<InvalidDeployedShipException> {
            throw InvalidDeployedShipException("Test")
        }
    }
}
