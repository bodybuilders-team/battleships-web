package pt.isel.daw.battleships.http.utils

import pt.isel.daw.battleships.http.media.siren.Action

/**
 * The actions of the API.
 */
object Actions {

    private const val GET_METHOD = "GET"
    private const val POST_METHOD = "POST"

    val listUsers = Action(
        name = Rels.LIST_USERS,
        title = "List Users",
        method = GET_METHOD,
        href = Uris.users()
    )

    val register = Action(
        name = Rels.REGISTER,
        title = "Register",
        method = POST_METHOD,
        href = Uris.users()
    )

    val login = Action(
        name = Rels.LOGIN,
        title = "Login",
        method = POST_METHOD,
        href = Uris.usersLogin()
    )

    val logout = Action(
        name = Rels.LOGOUT,
        title = "Logout",
        method = POST_METHOD,
        href = Uris.usersLogout()
    )

    val refreshToken = Action(
        name = Rels.REFRESH_TOKEN,
        title = "Refresh Token",
        method = POST_METHOD,
        href = Uris.usersRefreshToken()
    )

    val listGames = Action(
        name = Rels.LIST_GAMES,
        title = "List Games",
        method = GET_METHOD,
        href = Uris.games()
    )

    val createGame = Action(
        name = Rels.CREATE_GAME,
        title = "Create Game",
        method = POST_METHOD,
        href = Uris.games()
    )

    val matchmake = Action(
        name = Rels.MATCHMAKE,
        title = "Matchmake",
        method = POST_METHOD,
        href = Uris.gamesMatchmake()
    )

    fun joinGame(gameId: Int) = Action(
        name = Rels.JOIN_GAME,
        title = "Join Game",
        method = POST_METHOD,
        href = Uris.gameJoin(gameId)
    )

    fun deployFleet(gameId: Int) = Action(
        name = Rels.DEPLOY_FLEET,
        title = "Deploy Fleet",
        method = POST_METHOD,
        href = Uris.myFleet(gameId)
    )

    fun getMyFleet(gameId: Int) = Action(
        name = Rels.GET_MY_FLEET,
        title = "Get My Fleet",
        method = GET_METHOD,
        href = Uris.myFleet(gameId)
    )

    fun getOpponentFleet(gameId: Int) = Action(
        name = Rels.GET_OPPONENT_FLEET,
        title = "Get Opponent Fleet",
        method = GET_METHOD,
        href = Uris.opponentFleet(gameId)
    )

    fun getMyShots(gameId: Int) = Action(
        name = Rels.GET_MY_SHOTS,
        title = "Get My Shots",
        method = GET_METHOD,
        href = Uris.myShots(gameId)
    )

    fun getOpponentShots(gameId: Int) = Action(
        name = Rels.GET_OPPONENT_SHOTS,
        title = "Get Opponent Shots",
        method = GET_METHOD,
        href = Uris.opponentShots(gameId)
    )

    fun fireShots(gameId: Int) = Action(
        name = Rels.FIRE_SHOTS,
        title = "Fire Shots",
        method = POST_METHOD,
        href = Uris.myShots(gameId)
    )

    fun getMyBoard(gameId: Int) = Action(
        name = Rels.GET_MY_BOARD,
        title = "Get My Board",
        method = GET_METHOD,
        href = Uris.myBoard(gameId)
    )

    fun leaveGame(gameId: Int) = Action(
        name = Rels.LEAVE_GAME,
        title = "Leave Game",
        method = POST_METHOD,
        href = Uris.leaveGame(gameId)
    )
}
