package pt.isel.daw.battleships.http.controllers.games.models.games.getGames

import pt.isel.daw.battleships.service.games.dtos.game.GamesDTO

/**
 * Represents the response body of a game list request.
 *
 * @property totalCount the total number of games
 */
data class GetGamesOutputModel(
    val totalCount: Int
) {
    constructor(gamesDTO: GamesDTO) : this(
        totalCount = gamesDTO.totalCount
    )
}
