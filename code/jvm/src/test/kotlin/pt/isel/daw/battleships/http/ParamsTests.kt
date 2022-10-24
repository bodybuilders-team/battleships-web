package pt.isel.daw.battleships.http

import pt.isel.daw.battleships.http.Params.LIMIT_PARAM
import pt.isel.daw.battleships.http.Params.OFFSET_PARAM
import pt.isel.daw.battleships.http.Params.ORDER_BY_PARAM
import pt.isel.daw.battleships.http.Params.SORT_DIRECTION_PARAM
import kotlin.test.Test
import kotlin.test.assertEquals

class ParamsTests {

    @Test
    fun `OFFSET_PARAM is correct`() {
        assertEquals("offset", OFFSET_PARAM)
    }

    @Test
    fun `LIMIT_PARAM is correct`() {
        assertEquals("limit", LIMIT_PARAM)
    }

    @Test
    fun `ORDER_BY_PARAM is correct`() {
        assertEquals("orderBy", ORDER_BY_PARAM)
    }

    @Test
    fun `SORT_DIRECTION_PARAM is correct`() {
        assertEquals("sortDirectionStr", SORT_DIRECTION_PARAM)
    }
}