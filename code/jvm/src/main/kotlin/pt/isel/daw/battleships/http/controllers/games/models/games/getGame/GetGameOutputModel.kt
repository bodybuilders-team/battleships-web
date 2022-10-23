package pt.isel.daw.battleships.http.controllers.games.models.games.getGame

import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModel
import pt.isel.daw.battleships.http.controllers.games.models.games.GameStateModel
import pt.isel.daw.battleships.http.controllers.games.models.games.PlayerModel
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO

/**
 * Represents a Get Game Output Model.
 *
 * @property id the id of the game
 * @property name the name of the session
 * @property creator the creator of the game
 * @property config the configuration of the game
 * @property state the state of the game
 * @property players the players of the game
 */
data class GetGameOutputModel(
    val id: Int,
    val name: String,
    val creator: String,
    val config: GameConfigModel,
    val state: GameStateModel,
    val players: List<PlayerModel>
) {
    constructor(gameDTO: GameDTO) : this(
        id = gameDTO.id,
        name = gameDTO.name,
        creator = gameDTO.creator,
        config = GameConfigModel(gameDTO.config),
        state = GameStateModel(gameDTO.state),
        players = gameDTO.players.map { PlayerModel(it) }
    )
}
