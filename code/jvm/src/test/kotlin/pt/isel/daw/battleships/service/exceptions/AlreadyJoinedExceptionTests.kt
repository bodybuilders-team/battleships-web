package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class AlreadyJoinedExceptionTests {

    @Test
    fun `AlreadyJoinedException creation is successful`() {
        AlreadyJoinedException("Test")
    }

    @Test
    fun `AlreadyJoinedException thrown successfully`() {
        assertFailsWith<AlreadyJoinedException> {
            throw AlreadyJoinedException("Test")
        }
    }
}