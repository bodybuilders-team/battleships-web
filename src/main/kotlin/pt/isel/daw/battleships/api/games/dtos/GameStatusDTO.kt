package pt.isel.daw.battleships.api.games.dtos

data class GameStatusDTO(
    val gamePhase: String,
    val turn: String,
    val round: Int?
)
