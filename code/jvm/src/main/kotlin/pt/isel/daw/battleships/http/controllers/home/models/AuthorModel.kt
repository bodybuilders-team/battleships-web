package pt.isel.daw.battleships.http.controllers.home.models

/**
 * An Author Model.
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
