package pt.isel.daw.battleships.service.utils

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

/**
 * A Pageable implementation that allows to specify an offset and a limit.
 *
 * @property offset the offset of the page
 * @property limit the limit of the page
 */
data class OffsetPageRequest(
    private val offset: Long,
    private val limit: Int,
    private val sort: Sort? = null
) : Pageable {

    init {
        require(offset >= MIN_OFFSET) { "Offset must be greater than or equal to $MIN_OFFSET" }
        require(limit >= MIN_LIMIT) { "Limit must be greater than or equal to $MIN_LIMIT" }
    }

    override fun getPageNumber(): Int = (offset / limit).toInt()

    override fun getPageSize(): Int = limit

    override fun getOffset(): Long = offset

    override fun getSort(): Sort = sort ?: Sort.unsorted()

    override fun next(): Pageable = OffsetPageRequest(
        offset = offset + limit,
        limit = limit
    )

    override fun previousOrFirst(): Pageable =
        if (hasPrevious())
            OffsetPageRequest(
                offset = if (offset >= limit) offset - limit else 0,
                limit = limit
            )
        else
            this

    override fun first(): Pageable = OffsetPageRequest(
        offset = 0,
        limit = limit
    )

    override fun withPage(pageNumber: Int): Pageable = OffsetPageRequest(
        offset = pageNumber * limit.toLong(),
        limit = limit
    )

    override fun hasPrevious(): Boolean = offset > 0

    companion object {
        private const val MIN_OFFSET = 0
        private const val MIN_LIMIT = 1
    }
}
