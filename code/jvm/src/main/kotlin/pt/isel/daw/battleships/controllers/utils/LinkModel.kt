package pt.isel.daw.battleships.controllers.utils

/**
 * Represents a link to a resource.
 *
 * @property rel the relation of the link
 * @property href the URI of the resource
 * @property method the HTTP method of the link
 * @property requiresAuth whether the link requires authentication
 */
data class LinkModel(
    val rel: String,
    val href: String,
    val method: String,
    val requiresAuth: Boolean
)
