package pt.isel.daw.battleships.service.users.utils

import org.springframework.data.domain.Sort
import pt.isel.daw.battleships.service.users.utils.UsersOrder.NUMBER_GAMES_PLAYED
import pt.isel.daw.battleships.service.users.utils.UsersOrder.POINTS

/**
 * The order of the users in a pagination.
 *
 * @property orderBy the field to order by
 *
 * @property POINTS order by points
 * @property NUMBER_GAMES_PLAYED order by number of games played
 */
enum class UsersOrder(private val orderBy: String) {
    POINTS("points"),
    NUMBER_GAMES_PLAYED("number_of_games_played");

    /**
     * Converts this [UsersOrder] to a [Sort.Order].
     *
     * @param ascending if the users should be ordered in ascending order
     */
    fun toSort(ascending: Boolean): Sort = Sort.by(
        /* direction = */ if (ascending) Sort.Direction.ASC else Sort.Direction.DESC,
        /* ...properties = */ this.orderBy
    )
}
