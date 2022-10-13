package pt.isel.daw.battleships.controllers.utils.siren

import org.springframework.http.MediaType
import java.net.URI

/**
 * A sub-entity is an entity that is part of another entity.
 * It is represented as a link with an embedded representation.
 */
sealed class SubEntity

/**
 * A sub-entity that is an embedded link.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property href the URI of the link
 * @property type the media type of the link (optional)
 * @property title the title of the link (optional)
 */
data class EmbeddedLink(
    val `class`: List<String>? = null,
    val rel: List<String>,
    val href: URI,
    val type: MediaType? = null,
    val title: String? = null
) : SubEntity()

/**
 * A sub-entity that is an embedded representation.
 * Embedded sub-entity representations retain all the characteristics of a standard entity.,
 * including a [rel] property.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property properties the properties of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @property actions the actions that can be performed on the entity (optional)
 * @property links the links to other entities (optional)
 */
data class EmbeddedSubEntity<T>(
    val `class`: List<String>? = null,
    val rel: List<String>,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val actions: List<Action>? = null,
    val links: List<Link>? = null
) : SubEntity()
