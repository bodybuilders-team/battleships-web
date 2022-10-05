package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.GamesDTO

/**
 * Represents the response body of a game list request.
 *
 * @property games the list of games
 */
data class GamesOutputModel(val games: List<GameModel>) {
    constructor(gamesDTO: GamesDTO) : this(gamesDTO.games.map { GameModel(it) })
}
