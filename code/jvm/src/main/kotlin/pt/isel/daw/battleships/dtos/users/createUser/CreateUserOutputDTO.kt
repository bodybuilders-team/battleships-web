package pt.isel.daw.battleships.dtos.users.createUser

data class CreateUserOutputDTO(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
