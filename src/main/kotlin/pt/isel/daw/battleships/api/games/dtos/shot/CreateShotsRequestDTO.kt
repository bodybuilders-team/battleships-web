package pt.isel.daw.battleships.api.games.dtos.shot

/**
 * Represents the input data for the create shots operation.
 *
 * @property shots the list of shots to be created.
 */
data class CreateShotsRequestDTO(val shots: List<ShotDTO>)
