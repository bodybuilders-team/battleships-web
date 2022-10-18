package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.GamesDTO

/**
 * Represents the response body of a game list request.
 *
 * @property totalCount the total number of games
 */
data class GamesOutputModel(
    val totalCount: Int
) {
    constructor(gamesDTO: GamesDTO) : this(totalCount = gamesDTO.totalCount)
}
