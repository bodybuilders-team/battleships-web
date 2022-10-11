package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.utils.LinkModel

/**
 * Represents the links of a game.
 *
 * @property links the links of the game
 */
data class GameLinksModel(
    val links: List<LinkModel>
) {
    constructor(gameId: Int) : this(
        listOf(
            LinkModel(
                rel = "self",
                href = "/games/$gameId",
                method = "GET",
                requiresAuth = false
            ),
            LinkModel(
                rel = "join",
                href = "/games/$gameId/join",
                method = "POST",
                requiresAuth = true
            ),
            LinkModel(
                rel = "state",
                href = "/games/$gameId/state",
                method = "GET",
                requiresAuth = false
            )
        )
    )
}
