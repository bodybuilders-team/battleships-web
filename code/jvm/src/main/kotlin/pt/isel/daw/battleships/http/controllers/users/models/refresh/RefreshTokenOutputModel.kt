package pt.isel.daw.battleships.http.controllers.users.models.refresh

import pt.isel.daw.battleships.dtos.users.refresh.RefreshTokenOutputDTO

data class RefreshTokenOutputModel(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(refreshTokenDTO: RefreshTokenOutputDTO) :
        this(refreshTokenDTO.accessToken, refreshTokenDTO.refreshToken)
}
