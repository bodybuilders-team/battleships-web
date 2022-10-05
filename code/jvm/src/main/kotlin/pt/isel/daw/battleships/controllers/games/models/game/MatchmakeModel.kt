package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.MatchmakeDTO

class MatchmakeModel(
    val game: GameModel,
    val created: Boolean
) {
    constructor(matchmakeDTO: MatchmakeDTO) : this(
        GameModel(matchmakeDTO.game),
        matchmakeDTO.created
    )
}
