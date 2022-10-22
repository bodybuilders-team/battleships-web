package pt.isel.daw.battleships.service.games.dtos.ship

/**
 * Represents a list of Output Ship DTOs.
 *
 * @property ships the list of ship DTOs
 */
data class DeployedFleetDTO(
    val ships: List<DeployedShipDTO>
)
