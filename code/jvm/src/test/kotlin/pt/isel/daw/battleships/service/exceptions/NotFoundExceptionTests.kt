package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class NotFoundExceptionTests {

    @Test
    fun `NotFoundException creation is successful`() {
        NotFoundException("Test")
    }

    @Test
    fun `NotFoundException thrown successfully`() {
        assertFailsWith<NotFoundException> {
            throw NotFoundException("Test")
        }
    }
}
