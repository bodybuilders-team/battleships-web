package pt.isel.daw.battleships.http.utils

/**
 * The parameters used in the API requests.
 */
object Params {
    const val OFFSET_PARAM = "offset"
    const val LIMIT_PARAM = "limit"
    const val ORDER_BY_PARAM = "orderBy"
    const val SORT_DIRECTION_PARAM = "sortDirection"

    const val OFFSET_DEFAULT = 0
    const val LIMIT_DEFAULT = 10

    const val SORT_DIR_ASCENDING = "ASC"
    const val SORT_DIR_DESCENDING = "DESC"
}
