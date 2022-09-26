package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.database.model.Game

/**
 * Represents a response with games.
 *
 * @property games the list of game responses
 */
data class GamesResponse(
    val games: List<Game>
)
