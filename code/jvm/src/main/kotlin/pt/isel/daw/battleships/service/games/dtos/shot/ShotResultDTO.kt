package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.games.Shot

/**
 * A Shot Result DTO.
 *
 * @property result the result of the shot
 */
data class ShotResultDTO(
    val result: String
) {
    constructor(shotResult: Shot.ShotResult) : this(
        result = shotResult.name
    )
}
