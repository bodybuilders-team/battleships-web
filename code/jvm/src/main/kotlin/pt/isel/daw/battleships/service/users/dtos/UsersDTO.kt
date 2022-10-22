package pt.isel.daw.battleships.service.users.dtos

/**
 * Represents a Users DTO.
 *
 * @property users the list of users DTOs
 * @property totalCount the total number of users
 */
data class UsersDTO(
    val users: List<UserDTO>,
    val totalCount: Int
)
