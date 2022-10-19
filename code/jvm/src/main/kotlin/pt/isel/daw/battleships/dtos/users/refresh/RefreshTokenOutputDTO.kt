package pt.isel.daw.battleships.dtos.users.refresh

data class RefreshTokenOutputDTO(
    val accessToken: String,
    val refreshToken: String
)
