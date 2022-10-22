package pt.isel.daw.battleships.service.games.dtos.shot

/**
 * Represents a list of Input Shots DTOs.
 *
 * @property shots the list of shots
 */
data class UnfiredShotsDTO(
    val shots: List<UnfiredShotDTO>
)
