package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidGameConfigExceptionTests {

    @Test
    fun `InvalidGameConfigException creation is successful`() {
        InvalidGameConfigException("Test")
    }

    @Test
    fun `InvalidGameConfigException thrown successfully`() {
        assertFailsWith<InvalidGameConfigException> {
            throw InvalidGameConfigException("Test")
        }
    }
}