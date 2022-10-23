package pt.isel.daw.battleships.http.controllers.home.models

/**
 * Represents a Version Control Repository Model.
 *
 * @property type the type of the repository, e.g. git
 * @property url the url of the repository
 */
data class VCRepositoryModel(
    val type: String = DEFAULT_TYPE,
    val url: String
) {
    companion object {
        private const val DEFAULT_TYPE = "git"
    }
}
