package pt.isel.daw.battleships.api.games.models

import pt.isel.daw.battleships.api.games.models.game.GameModel
import pt.isel.daw.battleships.services.games.dtos.MatchmakeDTO

class MatchmakeModel(
    val game: GameModel,
    val created: Boolean
) {
    constructor(matchmakeDTO: MatchmakeDTO) : this(
        GameModel(matchmakeDTO.game),
        matchmakeDTO.created
    )
}
