package pt.isel.daw.battleships.http.utils

import pt.isel.daw.battleships.http.media.siren.Link
import pt.isel.daw.battleships.http.media.siren.SubEntity
import java.net.URI

/**
 * The links of the API.
 */
object Links {

    /**
     * Creates a self link.
     *
     * @param href the link's href
     * @return the link
     */
    fun self(href: URI) = Link(
        rel = listOf(Rels.SELF),
        href = href
    )

    val home = Link(
        rel = listOf(Rels.HOME),
        href = Uris.home()
    )

    val userHome = Link(
        rel = listOf(Rels.USER_HOME),
        href = Uris.userHome()
    )

    /**
     * Creates a link to the game sub-entity.
     *
     * @param gameId the game's id
     * @return the link
     */
    fun game(gameId: Int) = SubEntity.EmbeddedLink(
        rel = listOf(Rels.GAME),
        href = Uris.gameById(gameId = gameId)
    )
}
