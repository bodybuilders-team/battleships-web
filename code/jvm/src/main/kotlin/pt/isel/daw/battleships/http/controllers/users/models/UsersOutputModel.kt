package pt.isel.daw.battleships.http.controllers.users.models

/**
 * Represents the response body of a user list request.
 *
 * @property totalCount the total number of users
 */
data class UsersOutputModel(
    val totalCount: Int
)
