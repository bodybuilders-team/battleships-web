package pt.isel.daw.battleships.http.controllers.games.models.players.fireShots

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.UnfiredShotModel
import javax.validation.constraints.Size

/**
 * Represents the input data for the create shots operation.
 *
 * @property shots the list of shots to be created
 */
data class FireShotsInputModel(
    @Size(min = MIN_SHOTS_COUNT, message = "The number of shots must be at least $MIN_SHOTS_COUNT")
    val shots: List<UnfiredShotModel>
) {

    companion object {
        private const val MIN_SHOTS_COUNT = 1
    }
}
