package pt.isel.daw.battleships.http.controllers.games.models.games.getGame

import pt.isel.daw.battleships.http.controllers.games.models.games.GameConfigModel
import pt.isel.daw.battleships.http.controllers.games.models.games.GameStateModel
import pt.isel.daw.battleships.http.controllers.games.models.games.PlayerModel
import pt.isel.daw.battleships.service.games.dtos.game.GameDTO
import javax.validation.constraints.Size

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

    @Size(
        min = MIN_NAME_LENGTH,
        max = MAX_NAME_LENGTH,
        message = "Name must be between $MIN_NAME_LENGTH and $MAX_NAME_LENGTH characters long."
    )
    val name: String,
    val creator: String,
    val config: GameConfigModel,
    val state: GameStateModel,

    @Size(
        min = MIN_PLAYERS_COUNT,
        max = MAX_PLAYERS_COUNT,
        message = "There must be between $MIN_PLAYERS_COUNT and $MAX_PLAYERS_COUNT players."
    )
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

    companion object {
        const val MIN_NAME_LENGTH = 1
        const val MAX_NAME_LENGTH = 40

        private const val MIN_PLAYERS_COUNT = 1
        private const val MAX_PLAYERS_COUNT = 2
    }
}
