package pt.isel.daw.battleships.service.utils

import org.springframework.data.domain.Sort
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OffsetPageRequestTests {

    @Test
    fun `OffsetPageRequest creation is successful`() {
        OffsetPageRequest(1, 2)
    }

    @Test
    fun `OffsetPageRequest creation throws if offset is negative`() {
        assertFailsWith<IllegalArgumentException> {
            OffsetPageRequest(-1, 2)
        }
    }

    @Test
    fun `OffsetPageRequest creation throws if limit is less than 1`() {
        assertFailsWith<IllegalArgumentException> {
            OffsetPageRequest(1, 0)
        }
    }

    @Test
    fun `getPageNumber returns the page number`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val pageNumber = offsetPageRequest.pageNumber

        assertEquals(0, pageNumber)
    }

    @Test
    fun `getPageSize returns the page size`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val pageSize = offsetPageRequest.pageSize

        assertEquals(2, pageSize)
    }

    @Test
    fun `getOffset returns the offset`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val offset = offsetPageRequest.offset

        assertEquals(1, offset)
    }

    @Test
    fun `getSort returns the sorting parameters`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val sort = offsetPageRequest.sort

        assertEquals(Sort.unsorted(), sort)
    }

    @Test
    fun `next returns the next page`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val nextPage = offsetPageRequest.next()

        assertEquals(OffsetPageRequest(3, 2), nextPage)
    }

    @Test
    fun `previousOrFirst returns the previous page if it exists`() {
        val offsetPageRequest = OffsetPageRequest(3, 2)

        val previousPage = offsetPageRequest.previousOrFirst()

        assertEquals(OffsetPageRequest(1, 2), previousPage)
    }

    @Test
    fun `previousOrFirst returns the first page if the previous page does not exist`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val previousPage = offsetPageRequest.previousOrFirst()

        assertEquals(OffsetPageRequest(0, 2), previousPage)
    }

    @Test
    fun `first returns the first page`() {
        val offsetPageRequest = OffsetPageRequest(9, 2)

        val previousPage = offsetPageRequest.first()

        assertEquals(OffsetPageRequest(0, 2), previousPage)
    }

    @Test
    fun `withPage returns the page with the given page number`() {
        val offsetPageRequest = OffsetPageRequest(1, 2)

        val page = offsetPageRequest.withPage(2)

        assertEquals(OffsetPageRequest(4, 2), page)
    }

    @Test
    fun `hasPrevious returns true if the page has a previous page`() {
        val offsetPageRequest = OffsetPageRequest(3, 2)

        val hasPrevious = offsetPageRequest.hasPrevious()

        assertEquals(true, hasPrevious)
    }
}
