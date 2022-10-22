package pt.isel.daw.battleships.http.controllers.users.models.getUsers

/**
 * Represents the response body of a user list request.
 *
 * @property totalCount the total number of users
 */
data class GetUsersOutputModel(
    val totalCount: Int
)
