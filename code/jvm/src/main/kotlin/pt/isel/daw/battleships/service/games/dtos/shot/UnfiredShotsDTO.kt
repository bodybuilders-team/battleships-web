package pt.isel.daw.battleships.service.games.dtos.shot

/**
 * An Unfired Shots DTO.
 *
 * @property shots the list of shots
 */
data class UnfiredShotsDTO(
    val shots: List<UnfiredShotDTO>
)
