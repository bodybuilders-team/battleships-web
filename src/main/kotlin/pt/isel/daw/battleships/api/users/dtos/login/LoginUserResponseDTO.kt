package pt.isel.daw.battleships.api.users.dtos.login

/**
 * Represents the response of a login request.
 *
 * @property token The token that identifies the user.
 */
data class LoginUserResponseDTO(val token: String)
