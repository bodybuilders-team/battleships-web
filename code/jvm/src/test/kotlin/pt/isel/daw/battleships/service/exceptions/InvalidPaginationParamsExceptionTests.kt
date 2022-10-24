package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidPaginationParamsExceptionTests {

    @Test
    fun `InvalidPaginationParamsException creation is successful`() {
        InvalidPaginationParamsException("Test")
    }

    @Test
    fun `InvalidPaginationParamsException thrown successfully`() {
        assertFailsWith<InvalidPaginationParamsException> {
            throw InvalidPaginationParamsException("Test")
        }
    }
}