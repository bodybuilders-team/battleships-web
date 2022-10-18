package pt.isel.daw.battleships.dtos.users

/**
 * Represents a list of User DTOs.
 *
 * @property users the list of users DTOs
 * @property totalCount the total number of users
 */
data class UsersDTO(
    val users: List<UserDTO>,
    val totalCount: Int
)
