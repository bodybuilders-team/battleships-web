package pt.isel.daw.battleships.service.games.dtos.ship

/**
 * Represents a Deployed Fleet DTO.
 *
 * @property ships the list of ship DTOs
 */
data class DeployedFleetDTO(
    val ships: List<DeployedShipDTO>
)
