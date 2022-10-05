package pt.isel.daw.battleships.api.games.models.game

import pt.isel.daw.battleships.services.games.dtos.PlayerDTO

data class PlayerModel(val username: String, val points: Int) {
    constructor(player: PlayerDTO) : this(player.username, player.points)
}
