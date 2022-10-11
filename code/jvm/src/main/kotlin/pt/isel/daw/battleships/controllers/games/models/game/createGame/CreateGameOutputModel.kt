package pt.isel.daw.battleships.controllers.games.models.game.createGame

import pt.isel.daw.battleships.controllers.utils.LinkModel

/**
 * Represents the response body of a game creation request.
 *
 * @property links the links to the created game
 */
data class CreateGameOutputModel(
    val links: List<LinkModel>
) {
    constructor(gameId: Int) : this(
        links = listOf(
            LinkModel(
                rel = "self",
                href = "/games/$gameId",
                method = "GET",
                requiresAuth = false
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
