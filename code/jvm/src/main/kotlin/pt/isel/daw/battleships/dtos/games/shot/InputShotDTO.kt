package pt.isel.daw.battleships.dtos.games.shot

import pt.isel.daw.battleships.dtos.games.CoordinateDTO

/**
 * Represents an input shot DTO.
 *
 * @property coordinate the coordinate of the shot
 */
data class InputShotDTO(val coordinate: CoordinateDTO)
