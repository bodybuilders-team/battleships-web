package pt.isel.daw.battleships.http

import org.springframework.web.util.UriTemplate
import java.net.URI

/**
 * Contains the URIs of the API.
 */
object Uris {

    const val HOME = "/"

    const val USERS = "/users"
    const val USERS_LOGIN = "/users/login"
    const val USERS_LOGOUT = "/users/logout"
    const val USERS_REFRESH_TOKEN = "/users/refresh-token"
    const val USERS_GET_BY_USERNAME = "/users/{username}"

    const val GAMES = "/games"
    const val GAMES_MATCHMAKE = "/games/matchmake"
    const val GAMES_GET_BY_ID = "/games/{gameId}"
    const val GAMES_GAME_STATE = "/games/{gameId}/state"
    const val GAMES_JOIN = "/games/{gameId}/join"

    const val PLAYERS_MY_FLEET = "/games/{gameId}/players/self/fleet"
    const val PLAYERS_OPPONENT_FLEET = "/games/{gameId}/players/opponent/fleet"
    const val PLAYERS_MY_SHOTS = "/games/{gameId}/players/self/shots"
    const val PLAYERS_OPPONENT_SHOTS = "/games/{gameId}/players/opponent/shots"
    const val PLAYERS_MY_BOARD = "/games/{gameId}/players/self/board"

    fun home(): URI = URI(HOME)

    fun users(): URI = URI(USERS)
    fun usersLogin(): URI = URI(USERS_LOGIN)
    fun usersLogout(): URI = URI(USERS_LOGOUT)
    fun usersRefreshToken(): URI = URI(USERS_REFRESH_TOKEN)
    fun userByUsername(username: String): URI = UriTemplate(USERS_GET_BY_USERNAME).expand(username)

    fun games(): URI = URI(GAMES)
    fun gamesMatchmake(): URI = URI(GAMES_MATCHMAKE)
    fun gameById(gameId: Int): URI = UriTemplate(GAMES_GET_BY_ID).expand(gameId)
    fun gameState(gameId: Int): URI = UriTemplate(GAMES_GAME_STATE).expand(gameId)
    fun gameJoin(gameId: Int): URI = UriTemplate(GAMES_JOIN).expand(gameId)

    fun myFleet(gameId: Int): URI = UriTemplate(PLAYERS_MY_FLEET).expand(gameId)
    fun opponentFleet(gameId: Int): URI = UriTemplate(PLAYERS_OPPONENT_FLEET).expand(gameId)
    fun myShots(gameId: Int): URI = UriTemplate(PLAYERS_MY_SHOTS).expand(gameId)
    fun opponentShots(gameId: Int): URI = UriTemplate(PLAYERS_OPPONENT_SHOTS).expand(gameId)
    fun myBoard(gameId: Int): URI = UriTemplate(PLAYERS_MY_BOARD).expand(gameId)
}
