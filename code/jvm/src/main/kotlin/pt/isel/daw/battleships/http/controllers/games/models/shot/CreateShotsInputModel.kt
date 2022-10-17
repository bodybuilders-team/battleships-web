package pt.isel.daw.battleships.http.controllers.games.models.shot

import javax.validation.constraints.Size

/**
 * Represents the input data for the create shots operation.
 *
 * @property shots the list of shots to be created
 */
data class CreateShotsInputModel(
    @Size(min = MIN_SHOTS_COUNT, message = "The number of shots must be at least $MIN_SHOTS_COUNT")
    val shots: List<InputShotModel>
) {

    companion object {
        private const val MIN_SHOTS_COUNT = 1
    }
}
