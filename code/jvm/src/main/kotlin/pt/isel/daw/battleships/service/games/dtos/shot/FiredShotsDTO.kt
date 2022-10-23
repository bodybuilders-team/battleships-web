package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.Shot

/**
 * Represents a Fired Shots DTO.
 *
 * @property shots the list of shots
 */
data class FiredShotsDTO(
    val shots: List<FiredShotDTO>
) {
    constructor(shots: Collection<Shot>) : this(
        shots = shots.map(::FiredShotDTO)
    )
}
