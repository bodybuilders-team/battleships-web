package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.controllers.utils.LinkModel
import pt.isel.daw.battleships.services.games.dtos.game.GameStateDTO

/**
 * Represents a game state.
 *
 * @property phase the current game phase
 * @property phaseEndTime the time when the current phase ends
 * @property round the current round
 * @property turn the current turn
 * @property winner the winner of the game
 */
data class GameStateModel(
    val phase: String,
    val phaseEndTime: Long,
    val round: Int?,
    val turn: String?,
    val winner: String?,
    val links: List<LinkModel>
) {
    constructor(gameId: Int, stateDTO: GameStateDTO) : this(
        stateDTO.phase,
        stateDTO.phaseEndTime.time,
        stateDTO.round,
        stateDTO.turn,
        stateDTO.winner,
        listOf(
            LinkModel(
                rel = "self",
                href = "/games/$gameId/state",
                method = "GET",
                requiresAuth = false
            ),
            LinkModel(
                rel = "game",
                href = "/games/$gameId",
                method = "GET",
                requiresAuth = false
            ),
            LinkModel(
                rel = "getMyFleet",
                href = "/games/$gameId/players/self/fleet",
                method = "GET",
                requiresAuth = true
            ),
            LinkModel(
                rel = "getOpponentFleet",
                href = "/games/$gameId/players/opponent/fleet",
                method = "GET",
                requiresAuth = true
            ),
            LinkModel(
                rel = "deployFleet",
                href = "/games/$gameId/players/self/fleet",
                method = "POST",
                requiresAuth = true
            ),
            LinkModel(
                rel = "getShots",
                href = "/games/$gameId/players/self/shots",
                method = "GET",
                requiresAuth = true
            ),
            LinkModel(
                rel = "getOpponentShots",
                href = "/games/$gameId/players/opponent/shots",
                method = "GET",
                requiresAuth = true
            ),
            LinkModel(
                rel = "shoot",
                href = "/games/$gameId/players/self/shots",
                method = "POST",
                requiresAuth = true
            )
        )
    )
}
