package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.shot.Shot

data class ShotResultDTO(val result: String) {
    constructor(result: Shot.ShotResult) : this(result.name)
}