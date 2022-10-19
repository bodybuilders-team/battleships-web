package pt.isel.daw.battleships.services.utils

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class OffsetPageRequest(
    private val offset: Long,
    private val limit: Int
) : Pageable {
    init {
        require(limit > 0) { "Limit must be greater than 0" }
        require(offset >= 0) { "Offset must be greater than or equal to 0" }
    }

    override fun getPageNumber(): Int = (offset / limit).toInt()

    override fun getPageSize(): Int = limit

    override fun getOffset(): Long = offset

    override fun getSort(): Sort = Sort.unsorted()

    override fun next(): Pageable = OffsetPageRequest(offset + limit, limit)

    override fun previousOrFirst(): Pageable =
        if (hasPrevious()) OffsetPageRequest(offset - limit, limit) else this

    override fun first(): Pageable = OffsetPageRequest(0, limit)

    override fun withPage(pageNumber: Int): Pageable = OffsetPageRequest(pageNumber * limit.toLong(), limit)

    override fun hasPrevious(): Boolean = offset > limit
}
