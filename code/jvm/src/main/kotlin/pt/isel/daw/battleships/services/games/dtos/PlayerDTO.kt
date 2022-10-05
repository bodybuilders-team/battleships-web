package pt.isel.daw.battleships.services.games.dtos

import pt.isel.daw.battleships.database.model.player.Player

class PlayerDTO(val username: String, val points: Int) {
    constructor(player: Player) : this(player.user.username, player.points)
}
