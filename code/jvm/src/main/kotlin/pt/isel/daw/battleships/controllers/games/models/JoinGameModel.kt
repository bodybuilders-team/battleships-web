package pt.isel.daw.battleships.controllers.games.models

import pt.isel.daw.battleships.controllers.utils.LinkModel

/**
 * Represents the response to the request to join a game.
 *
 * @property gameId the id of the joined game
 * @property links the links to the joined game
 */
data class JoinGameModel(
    val gameId: Int,
    val links: List<LinkModel>
) {
    constructor(gameId: Int) : this(
        gameId = gameId,
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
