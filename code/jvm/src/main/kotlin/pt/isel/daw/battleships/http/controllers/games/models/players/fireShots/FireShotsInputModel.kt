package pt.isel.daw.battleships.http.controllers.games.models.players.fireShots

import pt.isel.daw.battleships.http.controllers.games.models.players.shot.UnfiredShotModel
import javax.validation.constraints.Size

/**
 * A Fire Shots Input Model.
 *
 * @property shots the list of shots to be created
 */
data class FireShotsInputModel(
    @field:Size(min = MIN_SHOTS_COUNT, message = "The number of shots must be at least $MIN_SHOTS_COUNT")
    val shots: List<UnfiredShotModel>
) {

    companion object {
        private const val MIN_SHOTS_COUNT = 1
    }
}
