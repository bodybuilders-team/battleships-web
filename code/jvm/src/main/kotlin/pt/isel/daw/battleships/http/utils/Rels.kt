package pt.isel.daw.battleships.http.utils

/**
 * The link relations of the API.
 */
object Rels {
    const val SELF = "self"
    const val ITEM = "item"

    const val HOME = "home"

    const val USER_HOME = "user-home"
    const val USER = "user"
    const val LIST_USERS = "list-users"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val LOGOUT = "logout"
    const val REFRESH_TOKEN = "refresh-token"

    const val LIST_GAMES = "list-games"
    const val CREATE_GAME = "create-game"
    const val MATCHMAKE = "matchmake"
    const val JOIN_GAME = "join-game"
    const val LEAVE_GAME = "abort-game"
    const val GAME = "game"
    const val GAME_STATE = "game-state"

    const val DEPLOY_FLEET = "deploy-fleet"
    const val GET_MY_FLEET = "get-my-fleet"
    const val MY_FLEET = "my-fleet"
    const val GET_OPPONENT_FLEET = "get-opponent-fleet"
    const val GET_MY_SHOTS = "get-my-shots"
    const val GET_OPPONENT_SHOTS = "get-opponent-shots"
    const val FIRE_SHOTS = "fire-shots"
    const val GET_MY_BOARD = "get-my-board"
}
