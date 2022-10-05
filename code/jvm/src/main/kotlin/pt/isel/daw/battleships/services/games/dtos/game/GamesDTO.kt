package pt.isel.daw.battleships.services.games.dtos.game

/**
 * Represents a response with games.
 *
 * @property games the list of game responses
 */
data class GamesDTO(
    val games: List<GameDTO>
)
