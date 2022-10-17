package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.database.model.Shot

/**
 * Represents a Shot DTO.
 *
 * @property result the result of the shot
 */
data class ShotResultDTO(val result: String) {
    constructor(result: Shot.ShotResult) : this(result = result.name)
}
