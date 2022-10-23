package pt.isel.daw.battleships.service.users

import org.springframework.data.domain.Sort

/**
 * Represents a Users Order.
 *
 * @property orderBy the field to order by
 */
enum class UsersOrder(private val orderBy: String) {
    POINTS("points"),
    NUMBER_GAMES_PLAYED("number_of_games_played");

    fun toSort(ascending: Boolean): Sort {
        return Sort.by(
            if (ascending) {
                Sort.Direction.ASC
            } else {
                Sort.Direction.DESC
            },
            this.orderBy
        )
    }
}
