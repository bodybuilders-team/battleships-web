package pt.isel.daw.battleships.service.games.dtos.game

/**
 * Represents a list of Game DTOs.
 *
 * @property games the list of game DTOs
 * @property totalCount the total number of games
 */
data class GamesDTO(
    val games: List<GameDTO>,
    val totalCount: Int
)
