package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.utils.LinkModel
import pt.isel.daw.battleships.services.games.dtos.game.MatchmakeDTO

/**
 * Represents a matchmake.
 *
 * @property links the links of the game
 * @property wasCreated whether the game was created or not
 */
data class MatchmakeModel(
    val wasCreated: Boolean,
    val links: List<LinkModel>
) {
    constructor(matchmakeDTO: MatchmakeDTO) : this(
        matchmakeDTO.wasCreated,
        listOf(
            LinkModel(
                rel = "self",
                href = "/games/${matchmakeDTO.game.id}",
                method = "GET",
                requiresAuth = false
            ),
            LinkModel(
                rel = "state",
                href = "/games/${matchmakeDTO.game.id}/state",
                method = "GET",
                requiresAuth = false
            )
        )
    )
}
