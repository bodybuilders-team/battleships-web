package pt.isel.daw.battleships.controllers.home.models

/**
 * Represents the repository model.
 *
 * @property type the type of the repository, e.g. git
 * @property url the url of the repository
 */
data class RepositoryModel(
    val type: String = "git",
    val url: String
)
