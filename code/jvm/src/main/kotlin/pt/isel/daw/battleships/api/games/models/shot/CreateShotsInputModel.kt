package pt.isel.daw.battleships.api.games.models.shot

/**
 * Represents the input data for the create shots operation.
 *
 * @property shots the list of shots to be created.
 */
data class CreateShotsInputModel(val shots: List<ShotModel>)
