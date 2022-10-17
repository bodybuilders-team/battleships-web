package pt.isel.daw.battleships.http.controllers.home.models

/**
 * Represents the repository model.
 *
 * @property type the type of the repository, e.g. git
 * @property url the url of the repository
 */
data class RepositoryModel(
    val type: String = DEFAULT_TYPE,
    val url: String
) {
    companion object {
        private const val DEFAULT_TYPE = "git"
    }
}
