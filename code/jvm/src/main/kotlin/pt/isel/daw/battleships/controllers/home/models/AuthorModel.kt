package pt.isel.daw.battleships.controllers.home.models

/**
 * Represents the author model.
 *
 * @property name the name of the author
 * @property email the email of the author
 * @property github the link to the GitHub of the author
 */
data class AuthorModel(
    val name: String,
    val email: String,
    val github: String
)
