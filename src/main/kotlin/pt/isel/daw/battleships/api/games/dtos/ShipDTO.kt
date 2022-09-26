package pt.isel.daw.battleships.api.games.dtos

data class ShipDTO(
    val type: String,
    val state: String,
    val coordinate: CoordinateDTO?,
    val orientation: String? // TODO: Restrict to "VERTICAL" or "HORIZONTAL" with jackson
)
