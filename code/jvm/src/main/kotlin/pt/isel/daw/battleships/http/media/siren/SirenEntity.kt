package pt.isel.daw.battleships.http.media.siren

import org.springframework.http.MediaType

/**
 * Siren is a specification for representing hypermedia entities in JSON.
 *
 * @see <a href="https://github.com/kevinswiber/siren">Siren Specification</a>
 *
 * @property class the class of the entity (optional)
 * @property properties the properties of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @property actions the actions that can be performed on the entity (optional)
 * @property links the links to other entities (optional)
 * @property title the title of the entity (optional)
 */
data class SirenEntity<T>(
    val `class`: List<String>? = null,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val actions: List<Action>? = null,
    val links: List<Link>? = null,
    val title: String? = null
) {
    companion object {
        private const val APPLICATION_TYPE = "application"
        private const val SIREN_SUBTYPE = "vnd.siren+json"
        const val SIREN_MEDIA_TYPE = "$APPLICATION_TYPE/$SIREN_SUBTYPE"

        @Suppress("unused")
        val sirenMediaType = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
    }
}
