package pt.isel.daw.battleships.http

import org.springframework.web.util.UriTemplate
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals

class UrisTests {

    @Test
    fun `home returns correct Uri`() {
        val uri = URI(Uris.HOME)

        val homeUri = Uris.home()

        assertEquals(uri, homeUri)
    }

    @Test
    fun `userHome returns correct Uri`() {
        val uri = URI(Uris.USER_HOME)

        val userHomeUri = Uris.userHome()

        assertEquals(uri, userHomeUri)
    }

    @Test
    fun `users returns correct Uri`() {
        val uri = URI(Uris.USERS)

        val usersUri = Uris.users()

        assertEquals(uri, usersUri)
    }

    @Test
    fun `usersLogin returns correct Uri`() {
        val uri = URI(Uris.USERS_LOGIN)

        val usersLoginUri = Uris.usersLogin()

        assertEquals(uri, usersLoginUri)
    }

    @Test
    fun `usersLogout returns correct Uri`() {
        val uri = URI(Uris.USERS_LOGOUT)

        val usersLogoutUri = Uris.usersLogout()

        assertEquals(uri, usersLogoutUri)
    }

    @Test
    fun `usersRefreshToken returns correct Uri`() {
        val uri = URI(Uris.USERS_REFRESH_TOKEN)

        val usersRefreshTokenUri = Uris.usersRefreshToken()

        assertEquals(uri, usersRefreshTokenUri)
    }

    @Test
    fun `userByUsername returns correct Uri`() {
        val username = "username"
        val uri = UriTemplate(Uris.USERS_GET_BY_USERNAME).expand(username)

        val userByUsernameUri = Uris.userByUsername(username)

        assertEquals(uri, userByUsernameUri)
    }

    @Test
    fun `games returns correct Uri`() {
        val uri = URI(Uris.GAMES)

        val gamesUri = Uris.games()

        assertEquals(uri, gamesUri)
    }

    @Test
    fun `gamesMatchmake returns correct Uri`() {
        val uri = URI(Uris.GAMES_MATCHMAKE)

        val gamesMatchmakeUri = Uris.gamesMatchmake()

        assertEquals(uri, gamesMatchmakeUri)
    }

    @Test
    fun `gameById returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.GAMES_GET_BY_ID).expand(gameId)

        val gameByIdUri = Uris.gameById(gameId)

        assertEquals(uri, gameByIdUri)
    }

    @Test
    fun `gameState returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.GAMES_GAME_STATE).expand(gameId)

        val gameStateUri = Uris.gameState(gameId)

        assertEquals(uri, gameStateUri)
    }

    @Test
    fun `gameJoin returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.GAMES_JOIN).expand(gameId)

        val gameJoinUri = Uris.gameJoin(gameId)

        assertEquals(uri, gameJoinUri)
    }

    @Test
    fun `myFleet returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.PLAYERS_MY_FLEET).expand(gameId)

        val myFleetUri = Uris.myFleet(gameId)

        assertEquals(uri, myFleetUri)
    }

    @Test
    fun `opponentFleet returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.PLAYERS_OPPONENT_FLEET).expand(gameId)

        val opponentFleetUri = Uris.opponentFleet(gameId)

        assertEquals(uri, opponentFleetUri)
    }

    @Test
    fun `myShots returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.PLAYERS_MY_SHOTS).expand(gameId)

        val myShotsUri = Uris.myShots(gameId)

        assertEquals(uri, myShotsUri)
    }

    @Test
    fun `opponentShots returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.PLAYERS_OPPONENT_SHOTS).expand(gameId)

        val opponentShotsUri = Uris.opponentShots(gameId)

        assertEquals(uri, opponentShotsUri)
    }

    @Test
    fun `myBoard returns correct Uri`() {
        val gameId = 1
        val uri = UriTemplate(Uris.PLAYERS_MY_BOARD).expand(gameId)

        val myBoardUri = Uris.myBoard(gameId)

        assertEquals(uri, myBoardUri)
    }
}
