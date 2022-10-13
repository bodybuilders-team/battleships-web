package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.services.games.dtos.shot.ShotResultDTO

/**
 * Represents a model for a shot result.
 *
 * @property result the result of the shot
 */
data class ShotResultModel(val result: String) {
    constructor(shotResultDTO: ShotResultDTO) : this(shotResultDTO.result)

    /**
     * Converts the shot result model to a DTO.
     *
     * @return the shot result DTO
     */
    fun toShotResultDTO() = ShotResultDTO(result)
}