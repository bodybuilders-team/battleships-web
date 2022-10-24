package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class AlreadyExistsExceptionTests {

    @Test
    fun `AlreadyExistsException creation is successful`() {
        AlreadyExistsException("Test")
    }

    @Test
    fun `AlreadyExistsException thrown successfully`() {
        assertFailsWith<AlreadyExistsException> {
            throw AlreadyExistsException("Test")
        }
    }
}
