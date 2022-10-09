package pt.isel.daw.battleships.services.games.dtos.ship

/**
 * Represents a list of Output Ship DTOs.
 *
 * @property ships the list of ship DTOs
 */
data class OutputFleetDTO(
    val ships: List<OutputShipDTO>
)
