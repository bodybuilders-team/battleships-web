package pt.isel.daw.battleships.domain

import kotlin.test.Test

class CoordinateTests {

    @Test
    fun `Coordinate creation is successful`() {
        Coordinate('A', 1)
    }

    companion object {
        val defaultCoordinate
            get() = Coordinate('A', 1)
    }
}
