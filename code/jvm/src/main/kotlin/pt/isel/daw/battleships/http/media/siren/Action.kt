package pt.isel.daw.battleships.http.media.siren

import org.springframework.http.MediaType
import java.net.URI

/**
 * An action represents an action that can be performed on an entity.
 *
 * @property name the name of the action
 * @property class the class of the action (optional)
 * @property method the HTTP method of the action (optional)
 * @property href the URI of the action
 * @property title the title of the action (optional)
 * @property type the media type of the action (optional)
 * @property fields the fields of the action (optional)
 */
data class Action(
    val name: String,
    val `class`: List<String>? = null,
    val method: String? = null,
    val href: URI,
    val title: String? = null,
    val type: MediaType? = null,
    val fields: List<Field>? = null
) {
    /**
     * Represents a field that is part of an action.
     *
     * @property name the name of the field
     * @property class the class of the field (optional)
     * @property type the type of the field (optional)
     * @property value the value of the field (optional)
     * @property title the title of the field (optional)
     */
    data class Field(
        val name: String,
        val `class`: List<String>? = null,
        val type: String? = null,
        val value: String? = null,
        val title: String? = null
    )
}
