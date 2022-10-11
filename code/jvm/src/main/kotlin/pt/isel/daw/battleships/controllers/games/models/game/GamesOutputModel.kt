package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GamesDTO

/**
 * Represents the response body of a game list request.
 *
 * @property games the list of games
 * @property totalCount the total number of games
 */
data class GamesOutputModel(
    val games: List<GameLinksModel>,
    val totalCount: Int
) {
    constructor(gamesDTO: GamesDTO) : this(
        games = gamesDTO.games.map { GameLinksModel(it.id) },
        gamesDTO.totalCount
    )
}
