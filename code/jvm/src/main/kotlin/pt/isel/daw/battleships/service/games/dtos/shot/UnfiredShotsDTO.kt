package pt.isel.daw.battleships.service.games.dtos.shot

/**
 * Represents an Unfired Shots DTO.
 *
 * @property shots the list of shots
 */
data class UnfiredShotsDTO(
    val shots: List<UnfiredShotDTO>
)
