package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidShotExceptionTests {

    @Test
    fun `InvalidShotException creation is successful`() {
        InvalidShotException("Test")
    }

    @Test
    fun `InvalidShotException thrown successfully`() {
        assertFailsWith<InvalidShotException> {
            throw InvalidShotException("Test")
        }
    }
}