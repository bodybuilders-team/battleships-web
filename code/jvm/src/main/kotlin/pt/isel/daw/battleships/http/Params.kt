package pt.isel.daw.battleships.http

/**
 * Contains the parameters used in the application.
 */
object Params {
    const val OFFSET_PARAM = "offset"
    const val LIMIT_PARAM = "limit"
    const val ORDER_BY_PARAM = "orderBy"
    const val SORT_DIRECTION_PARAM = "sortDirectionStr"

    const val OFFSET_DEFAULT = 0
    const val LIMIT_DEFAULT = 10

    const val SORT_DIRECTION_ASCENDING = "ASC"
    const val SORT_DIRECTION_DESCENDING = "DESC"
}
