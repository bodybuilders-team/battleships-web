package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidFiredShotExceptionTests {

    @Test
    fun `InvalidShotException creation is successful`() {
        InvalidFiredShotException("Test")
    }

    @Test
    fun `InvalidShotException thrown successfully`() {
        assertFailsWith<InvalidFiredShotException> {
            throw InvalidFiredShotException("Test")
        }
    }
}
