package pt.isel.daw.battleships.dtos.users.login

data class LoginUserOutputDTO(
    val accessToken: String,
    val refreshToken: String
)
