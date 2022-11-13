package pt.isel.daw.battleships.http.controllers.home.models.getHome

import pt.isel.daw.battleships.http.controllers.home.models.AuthorModel
import pt.isel.daw.battleships.http.controllers.home.models.VCRepositoryModel

/**
 * A Get Home Output Model.
 *
 * @property title the title of the application
 * @property version the version of the application
 * @property description the description of the application
 * @property authors the authors of the application
 * @property repository the repository of the application
 */
data class GetHomeOutputModel(
    val title: String,
    val version: String,
    val description: String,
    val authors: List<AuthorModel>,
    val repository: VCRepositoryModel
)
