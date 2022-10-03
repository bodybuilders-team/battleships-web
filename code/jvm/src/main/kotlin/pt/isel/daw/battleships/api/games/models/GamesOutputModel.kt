package pt.isel.daw.battleships.api.games.models

import pt.isel.daw.battleships.api.games.models.game.GameModel
import pt.isel.daw.battleships.services.games.dtos.GamesDTO

data class GamesOutputModel(val games: List<GameModel>) {
    constructor(gamesDTO: GamesDTO) : this(gamesDTO.games.map { GameModel(it) })
}
