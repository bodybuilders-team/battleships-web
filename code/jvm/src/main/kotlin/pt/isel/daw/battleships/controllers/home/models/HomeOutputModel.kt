package pt.isel.daw.battleships.controllers.home.models

import pt.isel.daw.battleships.controllers.utils.LinkModel

/**
 * Represents the output model for the home page.
 *
 * @property title the title of the application
 * @property version the version of the application
 * @property description the description of the application
 * @property authors the authors of the application
 * @property repository the repository of the application
 * @property links the links of the application from the home page
 */
data class HomeOutputModel(
    val title: String,
    val version: String,
    val description: String,
    val authors: List<AuthorModel>,
    val repository: RepositoryModel,
    val links: List<LinkModel>
)
