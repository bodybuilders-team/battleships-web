package pt.isel.daw.battleships.services.games.dtos.shot

import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO

/**
 * Represents an input shot DTO.
 *
 * @property coordinate the coordinate of the shot
 */
data class InputShotDTO(val coordinate: CoordinateDTO)
