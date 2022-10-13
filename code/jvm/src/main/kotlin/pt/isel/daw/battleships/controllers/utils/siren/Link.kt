package pt.isel.daw.battleships.controllers.utils.siren

import org.springframework.http.MediaType
import java.net.URI

/**
 * A link represents a navigational transition.
 *
 * @property rel the relation of the link
 * @property class the class of the link (optional)
 * @property href the URI of the link
 * @property title the title of the link (optional)
 * @property type the media type of the link (optional)
 */
data class Link(
    val rel: List<String>,
    val `class`: List<String>? = null,
    val href: URI,
    val title: String? = null,
    val type: MediaType? = null
)
