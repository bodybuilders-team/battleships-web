package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.PlayerDTO

data class PlayerModel(val username: String, val points: Int) {
    constructor(player: PlayerDTO) : this(player.username, player.points)
}
